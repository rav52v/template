package main.java.utils.functions;

import main.java.tools.ConfigurationParser;
import main.java.utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

abstract class BaseFunction {
    Driver driver;
    final Logger log = LogManager.getLogger(this);
    final Path pathOutputFolder = Paths.get("src", "outputFolder");
    final Path pathInputFolder = Paths.get("src", "inputFolder");
    private final int PAGE_LOAD_TIME = ConfigurationParser.getInstance().getPageLoadTime();

    private Calendar calendar;
    private SimpleDateFormat time;

    BaseFunction() {
        driver = new Driver();
    }

    protected void waitForPageLoading() {
        // wait for Ajax actions to begin
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        changeImplicitlyWaitTime(0);

        getDefaultWebDriverWait().until(new ExpectedCondition<Boolean>() {

            private final int MAX_NO_JQUERY_COUNTER = 3;
            private int noJQueryCounter = 0;

            @Override
            public Boolean apply(WebDriver driver) {
                String documentReadyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState;");

                Long jQueryActive = (Long) ((JavascriptExecutor) driver)
                        .executeScript("if(window.jQuery) { return window.jQuery.active; } else { return -1; }");

                log.debug(String.format("waitForPageLoading -> document.readyState: %s, jQuery.active: %d", documentReadyState, jQueryActive));

                if (jQueryActive == -1) {
                    noJQueryCounter++;

                    if (noJQueryCounter >= MAX_NO_JQUERY_COUNTER) {
                        return true;
                    }

                } else {
                    noJQueryCounter = 0;
                }

                return "complete".equals(documentReadyState) && jQueryActive == 0;
            }
        });
        changeBackImplicitlyWaitTime();

        // wait for Ajax responses to be processed
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void waitForElement(WebElement element) {
        long startTime = System.currentTimeMillis();
        log.debug("Wait for element {" + getElementInfo(element) + "} to be visible");

        changeImplicitlyWaitTime(0);
        new WebDriverWait(driver.getDriver(), 20, 10).until(ExpectedConditions
                .visibilityOf(element));
        changeBackImplicitlyWaitTime();

        log.debug("Waiting completed, it took {" + getPastTimeInMillis(startTime) + " milliseconds}");
    }

    protected String getElementInfo(WebElement element) {
        return (element.toString()).replaceAll("(^.*-> )|(]$)", "");
    }

    protected void changeImplicitlyWaitTime(int milliSeconds) {
        driver.getDriver().manage().timeouts().implicitlyWait(milliSeconds, TimeUnit.MILLISECONDS);
    }

    protected void changeBackImplicitlyWaitTime() {
        driver.getDriver().manage().timeouts().implicitlyWait(ConfigurationParser.getInstance().
                getImplicitlyWaitTime(), TimeUnit.SECONDS);
    }

    protected long getPastTimeInMillis(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    protected String getEstTime(long startTime, int thingsDone, int allThings) {
        double percentDone = Double.parseDouble(String.format("%.2f", (100.0 * ((double) (thingsDone)
                / (double) allThings))).replaceAll(",", "."));
        double passedTimeInMinutes = (System.currentTimeMillis() - startTime) / 60000.0;
        double timeLeftInMinutes = (100.0 / percentDone) * (passedTimeInMinutes) - passedTimeInMinutes + 1.0;
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) timeLeftInMinutes);
        time = new SimpleDateFormat("HH:mm");

        return time.format(calendar.getTime());
    }

    protected void changePageLoadTimeout(int seconds) {
        driver.getDriver().manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
    }

    protected void changeBackPageLoadTimeout() {
        driver.getDriver().manage().timeouts().pageLoadTimeout(60000, TimeUnit.MILLISECONDS);
    }

    private WebDriverWait getDefaultWebDriverWait() {
        WebDriverWait defaultWebDriverWait = new WebDriverWait(driver.getDriver(), PAGE_LOAD_TIME);
        defaultWebDriverWait.ignoring(StaleElementReferenceException.class);

        return defaultWebDriverWait;
    }
}
