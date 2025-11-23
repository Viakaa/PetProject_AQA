package aqa.task16;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class TrelloBO {
    private static final String trelloKey = "a23989194d13c756c9259629f5376321";
    private static final String trelloToken = "ATTA52511a09278163a7d844934bc1b2b03454d63715efdc3b4e2c207326773dce831AFEB56A";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public TrelloCreateOrganizationModel createOrganization(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.trello.com/1/organizations?displayName=" + name + "&key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Organization Response: " + response.body());

            TrelloCreateOrganizationModel orgModel =
                    mapper.readValue(response.body(), TrelloCreateOrganizationModel.class);
            System.out.println("Organization object: " + orgModel);

            return orgModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TrelloCreateBoardModel createBoard(String name, String orgId) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.trello.com/1/boards/?name=" + name + "&idOrganization=" + orgId + "&key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Board Response: " + response.body());

            TrelloCreateBoardModel boardModel =
                    mapper.readValue(response.body(), TrelloCreateBoardModel.class);
            System.out.println("Board object: " + boardModel);

            return boardModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TrelloCreateListModel createList(String name, String boardId) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.trello.com/1/lists?name=" + name + "&idBoard=" + boardId + "&key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create List Response: " + response.body());

            TrelloCreateListModel listModel =
                    mapper.readValue(response.body(), TrelloCreateListModel.class);
            System.out.println("List object: " + listModel);

            return listModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TrelloCreateCardModel createCard(String listId, String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.trello.com/1/cards?idList=" + listId + "&name=" + name + "&key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Create Card Response: " + response.body());

            TrelloCreateCardModel cardModel =
                    mapper.readValue(response.body(), TrelloCreateCardModel.class);
            System.out.println("Card object: " + cardModel);

            return cardModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public TrelloCreateCardModel updateCardDueDate(String cardId, String newDueDate) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString("{\"due\":\"" + newDueDate + "\"}"))
                .uri(URI.create("https://api.trello.com/1/cards/" + cardId + "?key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Update Due Date Response: " + response.body());

            TrelloCreateCardModel updateModel =
                    mapper.readValue(response.body(), TrelloCreateCardModel.class);
            System.out.println("Updated Card object: " + updateModel);

            return updateModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TrelloCreateCardModel deleteCardDueDate(String cardId) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString("{\"due\": null}"))
                .uri(URI.create("https://api.trello.com/1/cards/" + cardId + "?key=" + trelloKey + "&token=" + trelloToken))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Delete Due Date Response: " + response.body());

            TrelloCreateCardModel deleteModel =
                    mapper.readValue(response.body(), TrelloCreateCardModel.class);
            System.out.println("Deleted Card Due Date object: " + deleteModel);

            return deleteModel;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateCreateOrganizationRes(TrelloCreateOrganizationModel actual, TrelloCreateOrganizationModel expected) {
        SoftAssert soft = new SoftAssert();
        soft.assertNotNull(actual.getId());
        soft.assertEquals(actual.getDisplayName(), expected.getDisplayName());
        soft.assertAll();
    }

    public void validateCreateBoardRes(TrelloCreateBoardModel actual, TrelloCreateBoardModel expected) {
        SoftAssert soft = new SoftAssert();
        soft.assertNotNull(actual.getId());
        soft.assertEquals(actual.getName(), expected.getName());
        soft.assertAll();
    }

    public void validateCreateCardRes(TrelloCreateCardModel actual, TrelloCreateCardModel expected) {
        SoftAssert soft = new SoftAssert();
        soft.assertNotNull(actual.getId());
        soft.assertEquals(actual.getName(), expected.getName());
        soft.assertAll();
    }
}
