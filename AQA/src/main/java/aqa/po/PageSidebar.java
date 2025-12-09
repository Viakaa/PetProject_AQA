package aqa.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageSidebar {
    private final WebDriverWait wait;

    @FindBy(id = "n-randompage")
    private WebElement randomPageLink;

    public PageSidebar(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickRandomArticle() {
        wait.until(ExpectedConditions.elementToBeClickable(randomPageLink)).click();
    }
}