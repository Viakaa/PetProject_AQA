package aqa.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageHeader {
    private final WebDriverWait wait;

    @FindBy(css = "#pt-watchlist-2 a")
    private WebElement watchlistLink;

    @FindBy(xpath = "//a[contains(@href, 'Special:Watchlist')]")
    private WebElement universalWatchlistLink;

    @FindBy(css = "#pt-userpage-2 a span")
    private WebElement usernameLabel;

    @FindBy(id = "vector-user-links-dropdown")
    private WebElement userDropdown;

    @FindBy(xpath = "//span[text()='Log out']/..")
    private WebElement logoutLink;

    public PageHeader(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void openWatchlist() {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(universalWatchlistLink)).click();
        } catch (Exception e) {
            System.out.println("Could not find universal watchlist link, trying specific ID...");
            wait.until(ExpectedConditions.elementToBeClickable(watchlistLink)).click();
        }
    }

    public void logout() {
        try {
            if (userDropdown.isDisplayed()) {
                userDropdown.click();
            }
        } catch (Exception e) {
        }

        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();
    }
}