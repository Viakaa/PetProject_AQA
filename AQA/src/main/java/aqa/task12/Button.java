package aqa.task12;

import org.openqa.selenium.WebElement;

public class Button extends Element {

    public Button(WebElement webElement) {
        super(webElement);
    }

    @Override
    public void click() {
        validate();
        System.out.println("Clicking button: " + webElement.getText());
        webElement.click();
    }
}
