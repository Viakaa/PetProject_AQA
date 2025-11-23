package aqa.task12;
//General
//Implement PageFactory for a few pages.
//Implement a Wrapper for common WebElements (choose your variant).
//Implement methods for your web element with console logging. (Choose your variant with specific methods)
//Use those methods in a simple TC scenario
//Checkboxes:
//check - checks a checkbox
//uncheck - unchecks a checkbox
//isSelected - checks if the checkbox is selected
import aqa.task11.DriverProvider;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Task12Test {
    WebDriver driver;

    @BeforeTest
    void setUp() {
        ChromeDriverManager.getInstance().setup();
        DriverProvider.driver = new ChromeDriver();

    }

    @Test
    public void test12() {
        CheckBoxBusinessObject businessObject = new CheckBoxBusinessObject();
        businessObject.checkBox();
    }

    @AfterTest
    public void tearDown() {
        DriverProvider.quitDriver();
    }
}
