package aqa.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import java.time.Duration;

public class RandomArticlePage {
    private final WebDriverWait wait;

    @FindBy(id = "firstHeading")
    private WebElement heading;

    @FindBy(id = "p-lang-btn")
    private WebElement languageButton;

    private final By firstLanguageLocator = By.cssSelector("li.interlanguage-link a");

    public RandomArticlePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String switchToFirstLanguage() {
        wait.until(ExpectedConditions.elementToBeClickable(languageButton)).click();

        try {
            WebElement firstLang = wait.until(ExpectedConditions.elementToBeClickable(firstLanguageLocator));

            String newUrl = firstLang.getAttribute("href");
            System.out.println("Clicking on language: " + firstLang.getText() + " | URL: " + newUrl);

            firstLang.click();

            return newUrl;
        } catch (Exception e) {
            throw new SkipException("Language menu opened, but no languages found.");
        }
    }
}