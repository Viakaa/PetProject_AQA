package aqa.api;

import aqa.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ExtractIntroApiTest {

    @Test(groups = {"api"})
    public void extractIntroTest() throws Exception {

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

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        Allure.addAttachment("Extract API Response", "application/json", json);

        String extract = response.getQuery().getPages().values().iterator().next().getExtract();

        assertNotNull(extract);
        assertTrue(extract.toLowerCase().contains("testing"));
    }
}
