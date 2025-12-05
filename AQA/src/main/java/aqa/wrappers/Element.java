package aqa.wrappers;

import org.openqa.selenium.WebElement;

public class Element {
    protected WebElement element;

    public Element(WebElement element) {
        this.element = element;
    }

    public void click() {
        element.click();
    }

    public void type(String text) {
        element.clear();
        element.sendKeys(text);
    }

    public String getText() {
        return element.getText();
    }
}
