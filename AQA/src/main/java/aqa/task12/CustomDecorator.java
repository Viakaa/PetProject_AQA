package aqa.task12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class CustomDecorator implements FieldDecorator {
    private final ElementLocatorFactory factory;

    public CustomDecorator(WebDriver driver) {
        this.factory = new DefaultElementLocatorFactory(driver);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (field.getType().isAssignableFrom(Checkbox.class)) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }

            WebElement proxy = (WebElement) Proxy.newProxyInstance(
                    loader,
                    new Class[]{WebElement.class},
                    (proxy1, method, args) -> method.invoke(locator.findElement(), args)
            );

            return new Checkbox(proxy);
        }

        if (WebElement.class.isAssignableFrom(field.getType())) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }

            return Proxy.newProxyInstance(
                    loader,
                    new Class[]{WebElement.class},
                    (proxy, method, args) -> method.invoke(locator.findElement(), args)
            );
        }

        return null;
    }

}
