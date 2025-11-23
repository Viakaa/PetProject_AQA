package aqa.task12;

import org.openqa.selenium.WebElement;

public class Checkbox extends Element {

    public Checkbox(WebElement webElement) {
        super(webElement);
    }

    public void click() {
        validate();
        System.out.println("Clicking element");
        webElement.click();
    }

    public void check() {
        validate();
        if (!webElement.isSelected()) {
            System.out.println("Checking checkbox");
            webElement.click();
        } else {
            System.out.println("Checkbox already checked");
        }
    }

    public void uncheck() {
        validate();
        if (webElement.isSelected()) {
            System.out.println("Unchecking checkbox");
            webElement.click();
        } else {
            System.out.println("Checkbox already unchecked");
        }
    }

    public boolean isSelected() {
        validate();
        boolean selected = webElement.isSelected();
        System.out.println("Checkbox selected: " + selected);
        return selected;
    }

    private void validate() {
        if (!webElement.isDisplayed()) {
            throw new IllegalStateException("Element not visible on page!");
        }
        if (!webElement.isEnabled()) {
            throw new IllegalStateException("Element not enabled!");
        }
    }
}
