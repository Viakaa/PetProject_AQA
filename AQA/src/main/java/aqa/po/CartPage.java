package aqa.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;

public class CartPage {

    @FindBy(css = ".cart-item")
    private WebElement cartItem;

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isItemInCart() {
        return cartItem.isDisplayed();
    }
}
