package aqa.api;

import aqa.ConfigReader;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class WikipediaApiTests {

    @Test
    public void searchArticleTest() {

        SearchResponse response =
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
                        .extract().as(SearchResponse.class);

        assertFalse(response.query.search.isEmpty());
        assertTrue(response.query.search.get(0).pageid > 0);
        assertNotNull(response.query.search.get(0).title);
    }


    @Test
    public void extractIntroTest() {

        ExtractResponse response =
                given()
                        .baseUri(ConfigReader.GetProperty("base.url"))
                        .header("User-Agent", "AQA Test Suite / 1.0")
                        .queryParam("action", "query")
                        .queryParam("prop", "extracts")
                        .queryParam("exintro", "")
                        .queryParam("titles", "Software_testing")
                        .queryParam("format", "json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().as(ExtractResponse.class);

        String extract = response.query.pages.values().iterator().next().extract;

        assertNotNull(extract);
        assertTrue(extract.toLowerCase().contains("testing"));
    }


    @Test
    public void randomArticleInfoTest() {

        RandomResponse random =
                given()
                        .baseUri(ConfigReader.GetProperty("base.url"))
                        .header("User-Agent", "AQA Test Suite / 1.0")
                        .queryParam("action", "query")
                        .queryParam("list", "random")
                        .queryParam("rnlimit", "1")
                        .queryParam("format", "json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().as(RandomResponse.class);

        String randomTitle = random.query.random.get(0).title;

        ExtractResponse info =
                given()
                        .baseUri(ConfigReader.GetProperty("base.url"))
                        .header("User-Agent", "AQA Test Suite / 1.0")
                        .queryParam("action", "query")
                        .queryParam("prop", "info")
                        .queryParam("titles", randomTitle)
                        .queryParam("format", "json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().as(ExtractResponse.class);

        String returnedTitle = info.query.pages.values().iterator().next().title;

        assertEquals(randomTitle, returnedTitle);
    }
}
