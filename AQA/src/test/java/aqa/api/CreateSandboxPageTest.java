package aqa.api;

import aqa.ConfigReader;
import aqa.db.LoginDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.fail;

public class CreateSandboxPageTest {

    @Test(dataProvider = "users", dataProviderClass = LoginDataProvider.class, groups = {"api"})
    public void createSandboxPageTest(String username, String password) throws Exception {

        String baseUrl = ConfigReader.GetProperty("base.url");
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";
        Map<String, String> cookieMap = new HashMap<>();

        System.out.println("=== STEP 1: Requesting login token ===");
        Response tokenResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
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
            fail("Login Token not found! Response: " + tokenResponse.asString());
        }
        System.out.println("Login token received: " + loginToken);

        System.out.println("=== STEP 2: Logging in ===");
        Response loginResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
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
            fail("Login Failed! Reason: " + reason);
        }
        System.out.println("Login successful for user: " + username);

        System.out.println("=== STEP 3: Getting CSRF token ===");
        Response csrfResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
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
            fail("CSRF Token is null! Response: " + csrfResponse.asString());
        }
        System.out.println("CSRF token received: " + csrfToken);

        System.out.println("=== STEP 4: Creating page ===");
        String pageTitle = "User:" + username + "/sandbox/TestPage_Check";
        String pageContent = "Automated test create via RestAssured.";
        Response createPageResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
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
                fail("CAPTCHA required! Response: " + createPageResponse.asString());
            }
            fail("Edit failed with result: " + createPageResult + ". Response: " + createPageResponse.asString());
        }

        System.out.println("Page created successfully: " + pageTitle);
        System.out.println("=== STEP 5: Verifying page content ===");
        Response verifyResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
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
            System.out.println("Full verification response: " + responseBody);
            fail("Page content verification failed! Expected text '" + pageContent + "' not found.");
        }

        System.out.println("SUCCESS: Page content verified. Text found: '" + pageContent + "'");
    }


    private String joinCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}
