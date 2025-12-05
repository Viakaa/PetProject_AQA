package aqa.utils;

import org.sikuli.script.Screen;

public class ScreenHelper {
     public static void makeScreenshot(){
        new Screen().capture().save(".", "capture_" + System.currentTimeMillis() + ".png");
    }
}
