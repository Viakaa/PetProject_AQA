package aqa.api;

import aqa.ConfigReader;
import aqa.db.LoginDataProvider;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

public class ChangeUsersGenderApiTest {

    private static final Logger logger = LoggerFactory.getLogger(ChangeUsersGenderApiTest.class);

    @Test(dataProvider = "users", dataProviderClass = LoginDataProvider.class, groups = {"api"})
    public void changeGenderTest(String username, String password) throws Exception {

        String baseUrl = ConfigReader.GetProperty("base.url");
        Map<String, String> cookieMap = new HashMap<>();

        String targetGender = "female";

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
            fail("Login Token not found!");
        }

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
            fail("Login Failed!");
        }
        logger.info("Login successful.");

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
        if (csrfToken == null) fail("CSRF Token is null!");
        logger.info("CSRF Token received.");

        logger.info("=== STEP 4: Setting gender to '{}' ===", targetGender);
        Response changeResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "options")
                .formParam("optionname", "gender")
                .formParam("optionvalue", targetGender)
                .formParam("token", csrfToken)
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        String optionsResult = changeResponse.xmlPath().getString("api.@options");

        if (!"success".equalsIgnoreCase(optionsResult)) {
            logger.error("Failed to change option. Response: \n{}", changeResponse.asString());
            fail("Change option failed!");
        }
        logger.info("Option change request sent successfully.");

        logger.info("=== STEP 5: Verifying user info ===");
        Response verifyResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("meta", "userinfo")
                .queryParam("uiprop", "options")
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = verifyResponse.asString();
        XmlPath xmlPath = new XmlPath(responseBody);

        String actualGender = xmlPath.getString("api.query.userinfo.options.@gender");

        logger.info("Actual gender in profile: {}", actualGender);

        if (!targetGender.equals(actualGender)) {
            logger.error("Verification failed! Expected: {}, Actual: {}", targetGender, actualGender);
            fail("Gender verification failed!");
        }

        logger.info("Success: Gender successfully changed to '{}'", actualGender);
    }

    private String joinCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}