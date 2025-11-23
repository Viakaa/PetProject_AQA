package aqa.task21;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import java.io.File;

import static aqa.task21.ScreenHelper.makeScreenshot;

public class Task21Test {
    @Test
    void calculatorTest() throws FindFailed {
        Screen screen = new Screen();
        Pattern pattern = new Pattern(
                new File("src/main/resources/pattern/searchInput.png")
                        .getAbsolutePath());
        screen.find(pattern).click();

        screen.type("calc");
        screen.type(Key.ENTER);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        makeScreenshot();

        Pattern button2 = new Pattern(
                new File("src/main/resources/pattern/2.png")
                        .getAbsolutePath());
        screen.find(button2).click();

        Pattern dot = new Pattern(
                new File("src/main/resources/pattern/dot.png")
                        .getAbsolutePath());
        screen.find(dot).click();
        screen.find(button2).click();

        Pattern multiplication = new Pattern(
                new File("src/main/resources/pattern/multiplication.png")
                        .getAbsolutePath());
        screen.find(multiplication).click();

        screen.find(button2).click();
        screen.find(dot).click();
        screen.find(button2).click();

        Pattern equal = new Pattern(
            new File("src/main/resources/pattern/equal.png")
                    .getAbsolutePath());
        screen.find(equal).click();
        makeScreenshot();

        Pattern result = new Pattern(
            new File("src/main/resources/pattern/result.png")
                        .getAbsolutePath());

        screen.find(result).isObserving();
}
}
