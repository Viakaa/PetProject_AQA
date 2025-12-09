package aqa.po;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ArticlePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "firstHeading")
    private WebElement heading;

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void scrollToExternalLinks() {
        WebElement section = driver.findElement(By.id("External_links"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
    }

    public void openExternalLink(String linkText) {
        WebElement link = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='External_links']/../following-sibling::ul[1]//a[contains(text(),'" + linkText + "')]"))
        );
        link.click();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }
}
