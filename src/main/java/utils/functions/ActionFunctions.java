package main.java.utils.functions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionFunctions extends BaseFunction {

    protected void moveToElement(WebElement element) {
        new Actions(driver.getDriver()).moveToElement(element).perform();
    }
}
