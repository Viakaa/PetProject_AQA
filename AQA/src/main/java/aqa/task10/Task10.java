package aqa.task10;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

//General
//1. Set up ChromeDriver using the driver file and properties.
//2. Set up ChromeDriver using DriverManager.
//3. Navigate to the page assigned to your variant.
//4. Select any three different elements.
//5. Interact with all the selected elements.
//6. Verify the visibility of each element using assertions.
//7. Wrap all these steps into a TestNG test case.
public class Task10 {
    public static void main(String[] args) {
        File chromeDriverFile = new File("java_aqa/driver/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",
                chromeDriverFile.getAbsoluteFile().getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/");
    }


}
