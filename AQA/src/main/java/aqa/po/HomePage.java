package aqa.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(xpath = "//a[text()='browser automation']")
    private List<WebElement> links;

    @FindBy(css = "#mw-normal-catlinks ul li")
    private List<WebElement> categories;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void searchFor(String term) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(term);
        searchInput.submit();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFirstHeading() {
        return driver.getTitle();
    }

    public boolean isLinkPresent(String linkText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(links));
        for (WebElement link : links) {
            if (link.getText().equals(linkText)) {
                link.click();
                return true;
            }
        }
        return false;
    }

    public List<String> getCategories() {
        wait.until(ExpectedConditions.visibilityOfAllElements(categories));
        return categories.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
