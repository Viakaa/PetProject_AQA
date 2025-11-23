package aqa.task6;
//1: Create 3 simple unit tests for Task_2 (modify your code to have 3 different methods in Task_2 solving if needed).
//2. Create testng.xml which should execute your test class. Execute this file
//3. Add a Data provider for each test.
//4. Create a test with parameters loaded from testng.xml.

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static aqa.task6.Task6_8.*;

public class MainTest {
    @DataProvider
    public static Object[][] updatedDBData() {
        return new Object[][]{
                {"UpdatedCity1"},
                {"UpdatedCity2"},
                {"UpdatedCity3"}
        };
    }
    @Test
    public void CRUDDataBaseTest(){
        String testCity = "TestCity";
        String testState = "TestState";

        //create object
        Address newAddress = createAddress(testCity,testState);
        //check object is created
        Address actualAddress = read(newAddress.getId());

        Assert.assertEquals(actualAddress.getCity(), testCity, "Invalid city");
        Assert.assertEquals(actualAddress.getState(), testState, "Invalid state");

    }

    @Test(dataProvider = "updatedDBData")
    public void updateDataBaseTest(String updatedCity){

        Address addressOriginal = createAddress("OriginalCity", "OriginalState");

        //update object
        addressOriginal.setCity(updatedCity);
        update(addressOriginal);

        // check if object is updated
        Address actualAddress = read(addressOriginal.getId());
        Assert.assertEquals(actualAddress.getCity(), updatedCity, "Invalid city");

    }

    @Test
    @Parameters({"CityParameter", "StateParameter"})
    public void deleteParameterTest(String cityParam, String stateParam) {
        Address addressDelete = createAddress(cityParam, stateParam);
        Integer id = addressDelete.getId();

        //delete object
        delete(id);

        //check object is deleted
        Address deleted = read(id);
        Assert.assertNull(deleted, "Address is deleted");
    }


}
