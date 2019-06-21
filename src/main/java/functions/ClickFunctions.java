package main.java.functions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickFunctions extends BaseFunction {

  /**
   * This function is clicking element and enables you to manage timeout
   *
   * @param element     WebElement, you want to click
   * @param maxWaitTime max waiting time for element in seconds
   */
  public void clickOn(WebElement element, long... maxWaitTime) {
    log.debug("Click element {" + getElementInfo(element) + "}.");
    long timeOutInSeconds = maxWaitTime.length > 0 ? maxWaitTime[0] : DEFAULT_WEB_DRIVER_WAIT_TIME;
    changeImplicitlyWaitTime(0);
    new WebDriverWait(driver.getDriver(), timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
    turnOnImplicitlyWaitTime();
    try {
      click(element);
    } catch (WebDriverException e) {
      log.debug("Problem with click noticed, trying to scroll into viewport, then repeat.");
      scrollIntoView(element);
      sleeper(1000);
      click(element);
    }
  }

  /**
   * This function is clicking element and enables you to manage timeout
   *
   * @param by          locator to WebElement, you want to click
   * @param maxWaitTime max waiting time for element in seconds
   */
  public void clickOn(By by, long... maxWaitTime) {
    log.debug("Click element {" + by + "}.");
    long timeOutInSeconds = maxWaitTime.length > 0 ? maxWaitTime[0] : DEFAULT_WEB_DRIVER_WAIT_TIME;
    changeImplicitlyWaitTime(0);
    new WebDriverWait(driver.getDriver(), timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(by));
    turnOnImplicitlyWaitTime();
    WebElement element = driver.getDriver().findElement(by);
    try {
      click(element);
    } catch (WebDriverException e) {
      log.debug("Problem with click noticed, trying to scroll into viewport, then repeat.");
      scrollIntoView(element);
      sleeper(1000);
      click(element);
    }
  }

  public void clickUsingJavaScript(WebElement element) {
    log.debug("Click element {" + getElementInfo(element) + "} using JavaScript.");
    ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click()", element);
  }

  public void clickUsingJavaScript(By by) {
    log.debug("Click element {" + by + "} using JavaScript.");
    ((JavascriptExecutor) driver.getDriver())
            .executeScript("arguments[0].click()", driver.getDriver().findElement(by));
  }

  public void clickAndWaitForElement(WebElement element, By by, long... maxWaitTime) {
    try {
      clickOn(element, maxWaitTime);
      changeImplicitlyWaitTime(0);
      new WebDriverWait(driver.getDriver(), DEFAULT_WEB_DRIVER_WAIT_TIME).until(ExpectedConditions
              .visibilityOfElementLocated(by));
      turnOnImplicitlyWaitTime();
    } catch (TimeoutException e) {
      log.error(e.toString());
      throw new RuntimeException(e.toString());
    }
  }

  public void moveToElementThenClickAnother(WebElement element, By by, long... maxWaitTime) {
    try {
      new Actions(driver.getDriver()).moveToElement(element).build().perform();
      clickOn(by, maxWaitTime);
    } catch (WebDriverException e) {
      log.error(e.toString());
      throw new RuntimeException(e.toString());
    }
  }

  public void moveToElementThenClickAnother(WebElement elementToMove, WebElement elementTClick, long... maxWaitTime) {
    try {
      new Actions(driver.getDriver()).moveToElement(elementToMove).build().perform();
      clickOn(elementTClick, maxWaitTime);
    } catch (WebDriverException e) {
      log.error(e.toString());
      throw new RuntimeException(e.toString());
    }
  }

  public void clickNTimes(WebElement element, int number, long... maxWaitTime) {
    log.debug("Click element {" + number + "} times {" + getElementInfo(element) + "}.");
    for (int i = 0; i < number; i++) clickOn(element, maxWaitTime);
  }

  public void doubleClick(WebElement element) {
    log.debug("Double click element {" + getElementInfo(element) + "}.");
    new Actions(driver.getDriver()).doubleClick(element).perform();
  }

  private void click(WebElement element) {
    new Actions(driver.getDriver()).moveToElement(element).click().perform();
  }
}