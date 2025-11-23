package aqa.task14;

import aqa.task11.DriverProvider;
import aqa.task12.CheckBoxBusinessObject;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
 import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({CustomListener.class, CustomAllureListener.class})
public class Task14Test {

    @BeforeTest
    void setUp() {
        ChromeDriverManager.getInstance().setup();
        DriverProvider.driver = new ChromeDriver();
        System.out.println("Setup done");
    }

    @Test
    public void test14() {
        CheckBoxBusinessObject businessObject = new CheckBoxBusinessObject();
        businessObject.checkBox();
    }

    @Test
    public void test14Fail() {
        CheckBoxBusinessObject businessObject = new CheckBoxBusinessObject();
        businessObject.checkBox();
        Assert.fail("Test Failed Message");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("Driver quit done");
        DriverProvider.quitDriver();
    }
}
