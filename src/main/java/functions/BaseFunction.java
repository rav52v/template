package main.java.functions;

import main.java.enums.Packages;
import main.java.utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static main.java.utils.ConfigService.getConfigService;

abstract class BaseFunction {
  Driver driver;
  final Logger log = LogManager.getLogger();
  final String PATH_TO_INPUT_FOLDER = Packages.INPUT_PACKAGE.getPackagePath();
  final String PATH_TO_OUTPUT_FOLDER = Packages.OUTPUT_PACKAGE.getPackagePath();
  final long DEFAULT_WEB_DRIVER_WAIT_TIME = getConfigService().getLongProperty("general.webDriverWait");

  BaseFunction() {
    driver = Driver.getDriverInstance();
  }

  public void sleeper(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void waitForPageLoading() {
    sleeper(1000);
    changeImplicitlyWaitTime(0);

    new WebDriverWait(driver.getDriver(), getConfigService().getLongProperty("general.pageLoadTime"))
            .ignoring(StaleElementReferenceException.class).until(new ExpectedCondition<Boolean>() {

      private final int MAX_NO_JQUERY_COUNTER = 3;
      private int noJQueryCounter = 0;

      @Override
      public Boolean apply(WebDriver driver) {
        String documentReadyState = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.readyState;");

        Long jQueryActive = (Long) ((JavascriptExecutor) driver)
                .executeScript("if(window.jQuery) { return window.jQuery.active; } else { return -1; }");

        if (!documentReadyState.equals("complete")) log.debug("Page loading: waiting for JavaScript...");
        if (jQueryActive == -1) log.debug("Page loading: waiting for jQuery...");

        if (jQueryActive == -1) {
          noJQueryCounter++;
          if (noJQueryCounter >= MAX_NO_JQUERY_COUNTER) return true;
        } else noJQueryCounter = 0;

        return "complete".equals(documentReadyState) && jQueryActive == 0;
      }
    });
    turnOnImplicitlyWaitTime();
    sleeper(500);
  }

  String getElInfo(WebElement element) {
    return (element.toString()).replaceAll("(^.*-> )|(]$)", "");
  }

  void changeImplicitlyWaitTime(int milliSeconds) {
    driver.getDriver().manage().timeouts().implicitlyWait(milliSeconds, TimeUnit.MILLISECONDS);
  }

  void turnOnImplicitlyWaitTime() {
    driver.getDriver().manage().timeouts().implicitlyWait(getConfigService()
            .getLongProperty("general.implicitlyWaitTime"), TimeUnit.SECONDS);
  }

  long getPassedTimeInMillis(long startTime) {
    return System.currentTimeMillis() - startTime;
  }

  String getEstTime(long startTime, int thingsDone, int allThings) {
    double percentDone = Double.parseDouble(String.format("%.2f", (100.0 * ((double) (thingsDone)
            / (double) allThings))).replaceAll(",", "."));
    double passedTimeInMinutes = (System.currentTimeMillis() - startTime) / 60000.0;
    double timeLeftInMinutes = (100.0 / percentDone) * (passedTimeInMinutes) - passedTimeInMinutes + 1.0;
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, (int) timeLeftInMinutes);
    return new SimpleDateFormat("HH:mm").format(calendar.getTime());
  }

  void scrollIntoView(WebElement element) {
    ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].scrollIntoView()", element);
  }
}