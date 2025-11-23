package aqa.task12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class CustomDecorator implements FieldDecorator {

    private final ElementLocatorFactory factory;

    public CustomDecorator(WebDriver driver) {
        this.factory = new DefaultElementLocatorFactory(driver);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        Class<?> fieldType = field.getType();

        if (Element.class.isAssignableFrom(fieldType)) {
            WebElement proxy = (WebElement) Proxy.newProxyInstance(
                    loader,
                    new Class[]{WebElement.class},
                    (proxy1, method, args) -> method.invoke(locator.findElement(), args)
            );

            try {
                return fieldType.getConstructor(WebElement.class).newInstance(proxy);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (WebElement.class.isAssignableFrom(fieldType)) {
            return Proxy.newProxyInstance(
                    loader,
                    new Class[]{WebElement.class},
                    (proxy, method, args) -> method.invoke(locator.findElement(), args)
            );
        }

        return null;
    }
}
