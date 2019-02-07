package main.java.utils.functions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CheckFunctions extends BaseFunction {

    public boolean isElementFound(WebElement element, int maxWaitTimeSec) {
        log.debug("Check if element {" + getElementInfo(element)
                + "} is found, max waiting time {" + maxWaitTimeSec + " seconds}");

        changeImplicitlyWaitTime(0);
        try {
            new WebDriverWait(driver.getDriver(), maxWaitTimeSec, 10).until(ExpectedConditions.visibilityOf(element));
            changeBackImplicitlyWaitTime();
            return true;
        } catch (TimeoutException e) {
            changeBackImplicitlyWaitTime();

            log.debug("Found not element");
            return false;
        }
    }

    public boolean isElementFound(List<WebElement> element, int maxWaitTimeMillis) {
        log.debug("Check if element is found (using list), max waiting time {"
                + maxWaitTimeMillis + " milliseconds}");


        changeImplicitlyWaitTime(maxWaitTimeMillis);
        if (!element.isEmpty()) {
            changeBackImplicitlyWaitTime();
            return true;
        } else {
            changeBackImplicitlyWaitTime();

            log.debug("Found not element");
            return false;
        }
    }

    public boolean containsRegexValue(WebElement element, String regex) {
        return element.getText().matches(regex);
    }

    public boolean containsRegexValue (List<WebElement> elementList, String regex) {
        for (WebElement element : elementList){
            if (element.getText().matches(regex))
                return true;
        }
        return false;
    }

    public boolean isElementClickable(WebElement element){
        changeImplicitlyWaitTime(0);
        try{
            new WebDriverWait(driver.getDriver(), 5, 40)
                    .until(ExpectedConditions.elementToBeClickable(element));
            changeBackImplicitlyWaitTime();
            return true;
        }catch(TimeoutException e){
            changeBackImplicitlyWaitTime();
            return false;
        }
    }

    public boolean isElementSelected(WebElement element) {
        return element.isSelected();
    }


}