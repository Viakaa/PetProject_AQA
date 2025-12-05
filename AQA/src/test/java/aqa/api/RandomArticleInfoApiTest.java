package aqa.api;

import aqa.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class RandomArticleInfoApiTest {

    @Test
    public void randomArticleInfoTest() throws Exception {

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

        String randomTitle = random.getQuery().getRandom().get(0).getTitle();

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

        // Серіалізація POJO у JSON для Allure
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);
        Allure.addAttachment("Extract API Response", "application/json", json);

        String returnedTitle = info.getQuery().getPages().values().iterator().next().getTitle();

        assertEquals(randomTitle, returnedTitle);
    }
}
