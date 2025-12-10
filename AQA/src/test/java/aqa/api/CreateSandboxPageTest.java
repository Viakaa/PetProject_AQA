package aqa.api;

import aqa.ConfigReader;
import aqa.db.LoginDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

public class CreateSandboxPageTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateSandboxPageTest.class);

    @Test(dataProvider = "users", dataProviderClass = LoginDataProvider.class, groups = {"api"})
    public void createSandboxPageTest(String username, String password) throws Exception {

        String baseUrl = ConfigReader.GetProperty("base.url");
        Map<String, String> cookieMap = new HashMap<>();

        logger.info("Starting CreateSandboxPageTest for user: {}", username);

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
            logger.error("Login Token not found! Response: \n{}", tokenResponse.asString());
            fail("Login Token not found!");
        }
        logger.info("Login token received");

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

        String result = loginResponse.xmlPath().getString("api.login.@result");
        if (!"Success".equals(result)) {
            String reason = loginResponse.xmlPath().getString("api.login.@reason");
            logger.error("Login Failed! Reason: {}", reason);
            fail("Login Failed! Reason: " + reason);
        }
        logger.info("Login successful for user: {}", username);

        logger.info("=== STEP 3: Getting CSRF token ===");
        Response csrfResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("meta", "tokens")
                .queryParam("type", "csrf")
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        if (!csrfResponse.getCookies().isEmpty()) {
            cookieMap.putAll(csrfResponse.getCookies());
            cookieHeader = joinCookies(cookieMap);
        }

        String csrfToken = csrfResponse.xmlPath().getString("api.query.tokens.@csrftoken");
        if (csrfToken == null) {
            logger.error("CSRF Token is null! Response: \n{}", csrfResponse.asString());
            fail("CSRF Token is null!");
        }
        logger.info("CSRF token received.");

        logger.info("=== STEP 4: Creating page ===");
        String pageTitle = "User:" + username + "/sandbox/TestPage_Check";
        String pageContent = "Automated test create via RestAssured.";

        Response createPageResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "edit")
                .formParam("title", pageTitle)
                .formParam("text", pageContent)
                .formParam("token", csrfToken)
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        String createPageResult = createPageResponse.xmlPath().getString("api.edit.@result");
        if (!"Success".equals(createPageResult)) {
            if (createPageResponse.xmlPath().get("api.edit.captcha") != null) {
                logger.error("Captcha required! Response: \n{}", createPageResponse.asString());
                fail("Captcha required!");
            }
            logger.error("Edit failed with result: {}. Response: \n{}", createPageResult, createPageResponse.asString());
            fail("Edit failed with result: " + createPageResult);
        }

        logger.info("Page created successfully: {}", pageTitle);

        logger.info("=== STEP 5: Verifying page content ===");
        Response verifyResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("prop", "extracts")
                .queryParam("explaintext", "1")
                .queryParam("titles", pageTitle)
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = verifyResponse.asString();
        if (!responseBody.contains(pageContent)) {
            logger.error("Page content verification failed! Expected text '{}' not found.", pageContent);
            logger.error("Full verification response: \n{}", responseBody);
            fail("Page content verification failed!");
        }

        logger.info("Sucess: Page content verified. Text found: '{}'", pageContent);
    }

    private String joinCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}