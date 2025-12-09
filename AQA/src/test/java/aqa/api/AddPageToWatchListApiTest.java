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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import static org.testng.Assert.assertTrue;

public class AddPageToWatchListApiTest {

    @Test(dataProvider = "users", dataProviderClass = LoginDataProvider.class, groups = {"api"})
    public void addPageToWatchlistTest(String username, String password) throws Exception {

        String baseUrl = ConfigReader.GetProperty("base.url");
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";
        Map<String, String> cookieMap = new HashMap<>();

        // ==================================================================================
        // КРОК 1 & 2: ЛОГІН (Стандартна процедура)
        // ==================================================================================
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

        if (loginToken == null) fail("Login Token not found!");

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
        cookieHeader = joinCookies(cookieMap); // Оновлюємо заголовок куків

        if (!"Success".equals(loginResponse.xmlPath().getString("api.login.@result"))) {
            fail("Login Failed! Reason: " + loginResponse.xmlPath().getString("api.login.@reason"));
        }
        System.out.println("Login successful.");

        // ==================================================================================
        // КРОК 3: ОТРИМАННЯ WATCH TOKEN (type=watch)
        // JMeter: action=query&meta=tokens&type=watch
        // ==================================================================================
        System.out.println("=== STEP 3: Getting Watch Token ===");
        Response watchTokenResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
                .header("Cookie", cookieHeader)
                .queryParam("action", "query")
                .queryParam("meta", "tokens")
                .queryParam("type", "watch") // Специфічний тип токена
                .queryParam("format", "xml")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        // Оновлюємо куки
        if (!watchTokenResponse.getCookies().isEmpty()) {
            cookieMap.putAll(watchTokenResponse.getCookies());
            cookieHeader = joinCookies(cookieMap);
        }

        // Шлях до watchtoken у XML
        String watchToken = watchTokenResponse.xmlPath().getString("api.query.tokens.@watchtoken");
        if (watchToken == null) {
            fail("Watch Token is null! Response: " + watchTokenResponse.asString());
        }
        System.out.println("Watch token received: " + watchToken);

        // ==================================================================================
        // КРОК 4: ДОДАВАННЯ ДО WATCHLIST (action=watch)
        // ==================================================================================
        System.out.println("=== STEP 4: Adding page to Watchlist ===");

        // Сторінка, яку будемо додавати
        String pageTitle = "User:" + username + "/sandbox/TestPage_Watchlist";

        Response watchResponse = given()
                .baseUri(baseUrl)
                .header("User-Agent", userAgent)
                .header("Cookie", cookieHeader)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("action", "watch")
                .formParam("titles", pageTitle)
                .formParam("token", watchToken) // Використовуємо саме watchToken
                .formParam("format", "xml")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Watch response: " + watchResponse.asString());

        // ==================================================================================
        // КРОК 5: ПЕРЕВІРКА
        // Успішна відповідь містить атрибут 'watched' у тезі <watch>
        // Приклад: <api><watch title="..." watched="" ... /></api>
        // ==================================================================================

        // Перевіряємо, чи є атрибут "watched" у відповіді
        // У RestAssured XmlPath перевірка наявності атрибута робиться через get() != null
        boolean isWatched = watchResponse.xmlPath().get("api.watch.@watched") != null;

        // Також перевіримо ім'я сторінки
        String watchedTitle = watchResponse.xmlPath().getString("api.watch.@title");

        assertTrue(isWatched, "Page was NOT added to watchlist! (Attribute 'watched' missing in response)");
        assertTrue(pageTitle.equalsIgnoreCase(watchedTitle), "Watched page title mismatch!");

        System.out.println("SUCCESS: Page '" + pageTitle + "' added to watchlist.");
    }

    private String joinCookies(Map<String, String> cookies) {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}