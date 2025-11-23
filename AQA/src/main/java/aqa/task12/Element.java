package aqa.task12;

import org.openqa.selenium.WebElement;

public class Element {
    protected WebElement webElement;

    public Element(WebElement webElement) {
        this.webElement = webElement;
    }

    public void click() {
        validate();
        webElement.click();
    }

    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    public String getText() {
        return webElement.getText();
    }

    protected void validate() {
        if (!webElement.isDisplayed()) {
            throw new IllegalStateException("Element not visible on page!");
        }
        if (!webElement.isEnabled()) {
            throw new IllegalStateException("Element not enabled!");
        }
    }
}
