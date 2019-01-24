package main.java.utils.functions;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GetFunctions extends BaseFunction {

    public String getTextFromElement(WebElement element) {
        log.debug("Get text from element {" + getElementInfo(element) + "}");

        String value = element.getAttribute("value");

        if (value == null)
            value = element.getText().trim();
        else
            value = value.trim();

        log.debug("Got value {" + (value.length() < 70 ? value : value.replaceAll("\\s", "")
                .substring(0, 70).concat("...")) + "}");
        return value;
    }

    public String getAttributeFromElement(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public String searchElementInElementAndGetText(WebElement element, By by) {
        log.debug("Search element located By {" + by + "} in element {"
                + getElementInfo(element) + "} and get text");

        String result = "";
        try {
            result = element.findElement(by).getText();
        } catch (StaleElementReferenceException e) {
            try {
                PageFactory.initElements(driver.getDriver(), this);
                Thread.sleep(2000);
                result = element.findElement(by).getText();
            } catch (StaleElementReferenceException x) {
                result = "";
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    public String searchElementInElementAndGetAttribute(WebElement element, By by, String attributeName) {
        log.debug("Search element located By {" + by + "} in element {"
                + getElementInfo(element) + "} and get attribute {" + attributeName + "}");

        String result = "";
        try {
            result = element.findElement(by).getAttribute(attributeName);
        } catch (StaleElementReferenceException e) {
            try {
                Thread.sleep(2000);
                result = element.findElement(by).getAttribute(attributeName);
            } catch (StaleElementReferenceException x) {
                result = "";
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    public String getCurrentUrl() {
        return driver.getDriver().getCurrentUrl();
    }

    public WebElement getElementContaingRegexValue(List<WebElement> elementList, String regex) {
        if (elementList.size() == 0) {
            log.error("Given list is empty");
            return null;
        }

        for (WebElement element : elementList) {
            if (element.getText().matches(regex))
                return element;
        }

        log.debug("List doesn't contain given regex value {" + regex + "}");
        return null;
    }
}
