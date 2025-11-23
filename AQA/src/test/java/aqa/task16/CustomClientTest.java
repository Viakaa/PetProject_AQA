package aqa.task16;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.UUID;

public class CustomClientTest {
    @Test
    public void trelloTest() throws IOException, InterruptedException {

        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        TrelloBO trelloBO = new TrelloBO();

        String orgName = "MyOrgTest";
        TrelloCreateOrganizationModel actualOrg = trelloBO.createOrganization(orgName);
        TrelloCreateOrganizationModel expectedOrg = new TrelloCreateOrganizationModel();
        expectedOrg.setDisplayName(orgName);
        trelloBO.validateCreateOrganizationRes(actualOrg, expectedOrg);

        String boardName = "MyBoardTest";
        TrelloCreateBoardModel actualBoard = trelloBO.createBoard(boardName, actualOrg.getId());
        TrelloCreateBoardModel expectedBoard = new TrelloCreateBoardModel();
        expectedBoard.setName(boardName);
        trelloBO.validateCreateBoardRes(actualBoard, expectedBoard);

        String listName = "MyListTest";
        TrelloCreateListModel actualList = trelloBO.createList(listName, actualBoard.getId());

        String cardName = "MyCardTest";
        TrelloCreateCardModel actualCard = trelloBO.createCard(actualList.getId(), cardName);
        TrelloCreateCardModel expectedCard = new TrelloCreateCardModel();
        expectedCard.setName(cardName);
        trelloBO.validateCreateCardRes(actualCard, expectedCard);

        String newDueDate = "2025-12-01T12:00:00.000Z";
        TrelloCreateCardModel updatedCard = trelloBO.updateCardDueDate(actualCard.getId(), newDueDate);

        TrelloCreateCardModel deletedDueDateCard = trelloBO.deleteCardDueDate(actualCard.getId());
    }
}
