package aqa.api;

import aqa.ConfigReader;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class SearchArticleApiTest {

    @Test(groups = {"api"})
    public void searchArticleTest() {

        Response response =
                given()
                        .baseUri(ConfigReader.GetProperty("base.url"))
                        .header("User-Agent", "AQA Test Suite / 1.0")
                        .queryParam("action", "query")
                        .queryParam("list", "search")
                        .queryParam("srsearch", "Java programming")
                        .queryParam("format", "json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().response();

        Allure.addAttachment("Search API Response", "application/json", response.getBody().asPrettyString());

        Reporter.getCurrentTestResult().setAttribute("responseBody", response.getBody().asPrettyString());

        SearchResponse searchResponse = response.as(SearchResponse.class);

        assertFalse(searchResponse.query.search.isEmpty());
        assertTrue(searchResponse.query.search.get(0).pageid > 0);
        assertNotNull(searchResponse.query.search.get(0).title);

    }
}
