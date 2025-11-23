package aqa.task12;

import io.qameta.allure.Step;

import static aqa.task11.DriverProvider.driver;

public class CheckBoxBusinessObject {

    private final CheckBoxPageObject page = new CheckBoxPageObject(driver);
    @Step
    public void checkBox() {
        driver.navigate().to("https://demoqa.com/elements");

        page.getCheckBoxMenu().click();
        page.getToggleButton().click();

        page.getDesktopCheckbox().check();
        page.getDownloadsCheckbox().check();

        page.getDesktopCheckbox().isSelected();
        page.getDownloadsCheckbox().isSelected();

        page.getDownloadsCheckbox().uncheck();
    }
}
