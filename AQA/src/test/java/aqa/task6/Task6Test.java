package aqa.task6;

import aqa.task8.Address;
import aqa.task8.Task6;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class Task6Test {

    @DataProvider
    public Object[][] updateCityData() {
        return new Object[][]{
                {"Kyiv"},
                {"Lviv"},
                {"Odesa"}
        };
    }

    @Test
    public void testCreateAndRead() {
        Address address = Task6.createAddress("TestCity", "TestState");
        Address fromDb = Task6.readAddress(address.getId());

        Assert.assertNotNull(fromDb);
        Assert.assertEquals(fromDb.getCity(), "TestCity");
        Assert.assertEquals(fromDb.getState(), "TestState");
    }

    @Test(dataProvider = "updateCityData")
    public void testUpdate(String newCity) {
        Address address = Task6.createAddress("OldCity", "OldState");

        address.setCity(newCity);
        Task6.updateAddress(address);

        Address updated = Task6.readAddress(address.getId());
        Assert.assertEquals(updated.getCity(), newCity);
    }

    @Test
    @Parameters({"CityParam", "StateParam"})
    public void testDelete(@Optional("XMLCity") String city,
                           @Optional("XMLState") String state) {
        Address address = Task6.createAddress(city, state);

        Task6.deleteAddress(address);

        Address deleted = Task6.readAddress(address.getId());
        Assert.assertNull(deleted);
    }
}
