package aqa.task16;

//General task
//Make restAssured Test Cases using scenario from Task_15
//The same using any another API client.
//Add Request and Response classes for each unique endpoints.
//Validate Response Object using your own class comparator.

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;

//General task
//Make restAssured Test Cases using scenario from Task_15
//The same using any another API client.
//Add Request and Response classes for each unique endpoints.
//Validate Response Object using your own class comparator.

public class Task16Test {

    @BeforeTest
    public void SetUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void RestAssuredTest () {
//        Step 1
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Response response = given()
                    .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                    .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                    .pathParam("organizationName", name )
                    .when()
                    .post("https://api.trello.com/1/organizations?displayName={organizationName}&key={trelloKey}&token={trelloToken}")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response();

            Assert.assertEquals(response.path("name"), name, "unexpected organization name");

        String organizationId = response.path("id");

//            Step 2
         response = given()
                .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                .pathParam("boardName", name)
                .pathParam("organizationId", organizationId)
                .when()
                .post("https://api.trello.com/1/boards/?name={boardName}&key={trelloKey}&token={trelloToken}&idOrganization={organizationId}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.path("name"), name, "unexpected board name");

        String boardId = response.path("id");

    //            Step 3
            response = given()
                        .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                    .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                    .pathParam("listName", name)
                    .pathParam("boardId", boardId)
                        .when()
                        .post("https://api.trello.com/1/lists?name={listName}&idBoard={boardId}&key={trelloKey}&token={trelloToken}")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertEquals(response.path("name"), name, "unexpected list name");

        String listId = response.path("id");

    //            Step 4
        response = given()
                .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                .pathParam("listId", listId)
                .body("{ \"due\": \"2025-10-25T12:00:00.000Z\" }")
                .when()
                .post("https://api.trello.com/1/cards?idList={listId}&key={trelloKey}&token={trelloToken}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();


        String cardId = response.path("id");

    //            Step 5
        response = given()
                .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                .pathParam("cardId", cardId)
                .body("{ \"due\": \"2025-10-26T12:00:00.000Z\" }")
                .when()
                .put("https://api.trello.com/1/cards/{cardId}?key={trelloKey}&token={trelloToken}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();


    //            Step 6
        response = given()
                .pathParam("trelloKey", ConfigReader.GetProperty("trelloKey"))
                .pathParam("trelloToken", ConfigReader.GetProperty("trelloToken"))
                .pathParam("cardId", cardId)
                .body("{ \"due\": null")
                .when()
                .put("https://api.trello.com/1/cards/{cardId}?key={trelloKey}&token={trelloToken}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

}

}
