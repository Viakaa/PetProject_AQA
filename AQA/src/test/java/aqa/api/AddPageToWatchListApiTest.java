package aqa.api;

import aqa.ConfigReader;
import aqa.db.LoginDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

public class AddPageToWatchListApiTest {
    private static final Logger logger = LoggerFactory.getLogger(AddPageToWatchListApiTest.class);

    @Test(dataProvider = "users", dataProviderClass = LoginDataProvider.class, groups = {"api"})
    public void addPageToWatchlistTest(String username, String password) throws Exception {

        String baseUrl = ConfigReader.GetProperty("base.url");
        Map<String, String> cookieMap = new HashMap<>();

        logger.info("=== STEP 1: Requesting login token ===");
        Response tokenResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "login")
                .formParam("lgname", username)
                .formParam("lgpassword", password)
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        cookieMap.putAll(tokenResponse.getCookies());
        String cookieHeader = joinCookies(cookieMap);
        String loginToken = tokenResponse.xmlPath().getString("api.login.@token");

        if (loginToken == null) {
            logger.error("Login Token not found! Response body: \n{}", tokenResponse.asString());
            fail("Login Token not found!");
        }
        logger.info("Login Token received.");

        logger.info("=== STEP 2: Logging in ===");
        Response loginResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "login")
                .formParam("lgname", username)
                .formParam("lgpassword", password)
                .formParam("lgtoken", loginToken)
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        cookieMap.putAll(loginResponse.getCookies());
        cookieHeader = joinCookies(cookieMap);

        if (!"Success".equals(loginResponse.xmlPath().getString("api.login.@result"))) {
            String reason = loginResponse.xmlPath().getString("api.login.@reason");
            logger.error("Login Failed! Reason: {}", reason);
            fail("Login Failed! Reason: " + reason);
        }
        logger.info("Login successful for user: {}", username);

        logger.info("=== STEP 3: Getting Watch Token ===");
        Response watchTokenResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("meta", "tokens")
                .queryParam("type", "watch")
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        if (!watchTokenResponse.getCookies().isEmpty()) {
            cookieMap.putAll(watchTokenResponse.getCookies());
            cookieHeader = joinCookies(cookieMap);
        }

        String watchToken = watchTokenResponse.xmlPath().getString("api.query.tokens.@watchtoken");
        if (watchToken == null) {
            logger.error("Watch Token is null! Response: \n{}", watchTokenResponse.asString());
            fail("Watch Token is null!");
        }
        logger.info("Watch Token received.");

        logger.info("=== STEP 4: Adding page to Watchlist ===");
        String pageTitle = "User:" + username + "/sandbox/TestPage_Watchlist";

        Response watchResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "watch")
                .formParam("titles", pageTitle)
                .formParam("token", watchToken)
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        String errorCode = watchResponse.xmlPath().getString("api.error.@code");
        if (errorCode != null) {
            String errorInfo = watchResponse.xmlPath().getString("api.error.@info");
            logger.error("API Error during Watch action! Code: {}, Info: {}", errorCode, errorInfo);
            fail("API Error during Watch action! Code: " + errorCode + ", Info: " + errorInfo);
        }

        boolean isWatchedResponse = watchResponse.xmlPath().getString("api.watch.w.@watched") != null;
        String watchedTitle = watchResponse.xmlPath().getString("api.watch.w.@title");

        if (!isWatchedResponse) {
            logger.error("Page was NOT added to watchlist. Response: \n{}", watchResponse.asString());
            fail("Page was NOT added to watchlist! (Attribute 'watched' missing in response)");
        }

        String normalizedExpectedTitle = pageTitle.replace("_", " ");
        if (!normalizedExpectedTitle.equalsIgnoreCase(watchedTitle)) {
            logger.error("Title mismatch! Expected: '{}', Actual: '{}'", normalizedExpectedTitle, watchedTitle);
            fail("Watched page title mismatch! Expected: " + normalizedExpectedTitle + ", Actual: " + watchedTitle);
        }

        logger.info("Success: Page added response confirmed.");


        logger.info("=== STEP 5: Verifying that page is added===");

        Response verifyResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("prop", "info")
                .queryParam("inprop", "watched")
                .queryParam("titles", pageTitle)
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        boolean isActuallyWatched = verifyResponse.xmlPath().getString("api.query.pages.page.@watched") != null;

        if (!isActuallyWatched) {
            logger.error("Verification failed: The page '{}' is NOT found in the user's watchlist. Response: \n{}", pageTitle, verifyResponse.asString());
            fail("Verification failed: The page '" + pageTitle + "' is NOT found in the user's watchlist.");
        }

        logger.info("Sucess: Page '{}' is in the watchlist.", pageTitle);
    }

    private String joinCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}