package main.java.utils.functions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;

public class InputFunctions extends BaseFunction {

    public void clickAndSendKeys(WebElement element, CharSequence... value) {
        log.debug("Click ones and send keys {" + Arrays.toString(value)
                + "} to element {" + getElementInfo(element) + "}");

        element.clear();
        new Actions(driver.getDriver()).moveToElement(element).click().sendKeys(value).perform();
    }

    public void sendKeysToElement(WebElement element, CharSequence... value) {
        log.debug("Send keys {" + Arrays.toString(value) + "} to element {"
                + getElementInfo(element) + "}");

        element.clear();
        new Actions(driver.getDriver()).moveToElement(element).sendKeys(value).perform();
    }

    public void sendKeys(CharSequence... value) {
        log.debug("Send keys {" + Arrays.toString(value) + "}");

        new Actions(driver.getDriver()).sendKeys(value).perform();
    }

}
