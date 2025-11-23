package aqa.task12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckBoxPageObject {

    WebDriver driver;

    @FindBy(xpath = "//li/span[text()='Check Box']")
    private Checkbox checkBoxMenu;

    @FindBy(xpath = "//button[contains(@class,'rct-collapse-btn')]")
    private Checkbox toggleButton;

    @FindBy(xpath = "//label[contains(@for, 'tree-node-desktop')]/span[contains(@class, 'rct-checkbox')]")
    private Checkbox desktopCheckbox;

    @FindBy(xpath = "//label[contains(@for, 'tree-node-downloads')]/span[contains(@class, 'rct-checkbox')]")
    private Checkbox downloadsCheckbox;

    public CheckBoxPageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new CustomDecorator(driver), this);
    }

    public Checkbox getCheckBoxMenu() {
        return checkBoxMenu;
    }

    public Checkbox getToggleButton() {
        return toggleButton;
    }

    public Checkbox getDesktopCheckbox() {
        return desktopCheckbox;
    }

    public Checkbox getDownloadsCheckbox() {
        return downloadsCheckbox;
    }
}
