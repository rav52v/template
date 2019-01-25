package main.java.utils.functions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class BrowserFunctions extends BaseFunction {


    public void openPage(String linkAddress) {
        log.debug("Loading page (expected) {" + linkAddress + "}");

        driver.getDriver().get(linkAddress);
        waitForPageLoading();

        log.debug("Current page {" + driver.getDriver().getCurrentUrl() + "}");
    }

    public void scrollPageBy(int widthToScroll, int heightToScroll) {
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("window.scrollBy(arguments[0],arguments[1])", widthToScroll, heightToScroll);
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    public void refreshPage() {
        driver.getDriver().navigate().refresh();
    }

    /**
     * @param elementList list of WebElements, which will be increase after scroll down.
     */
    public void scrollDownRefreshingPageTillEnd(List<WebElement> elementList) {
        long startTime = System.currentTimeMillis();
        int refreshingListSize;
        int refreshingListAfterSize;
        do {
            refreshingListSize = elementList.size();
            new Actions(driver.getDriver()).sendKeys(Keys.END).perform();
            waitForPageLoading();
            refreshingListAfterSize = elementList.size();
        }
        while (refreshingListAfterSize > refreshingListSize);

        log.debug("Found {" + elementList.size() + " elements}, operation took {" + getPastTimeInMillis(startTime) + "}");
    }
}