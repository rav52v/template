package main.java.utils.functions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class BrowserFunctions extends BaseFunction {

  private String mainWindowHandle;

  /**
   * Switches to window, which is not main window
   * This method support only one additional window
   */
  public void switchToSecondTab() {
    if (mainWindowHandle == null)
      mainWindowHandle = driver.getMainWindowHandle();
    for (String winHandle : driver.getDriver().getWindowHandles()) {
      if (!winHandle.equals(mainWindowHandle))
        driver.setDriver(driver.getDriver().switchTo().window(winHandle));
    }
  }

  /**
   * Switches back to main tab
   */
  public void switchToMainTab() {
    driver.setDriver(driver.getDriver().switchTo().window(mainWindowHandle));
  }

  /**
   * Closes focused tab
   */
  public void closeTab() {
    driver.getDriver().close();
  }

  /**
   * Opens new tab and focus
   */
  public void openNewTab() {
    ((JavascriptExecutor) driver.getDriver()).executeScript("window.open();");
    switchToSecondTab();
  }

  public void openPage(String linkAddress) {
    long startTime = System.currentTimeMillis();
    log.debug("Loading page (expected) {" + linkAddress + "}");

    driver.getDriver().get(linkAddress);
    waitForPageLoading();

    log.debug("Page loaded {" + driver.getDriver().getCurrentUrl() + "}, operation took {"
            + getPastTimeInMillis(startTime) + "}");
  }

  public void closeDriver(int delayMillis) {
    driver.afterTest(delayMillis);
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

  public Alert getAlertControl() {
    return driver.getDriver().switchTo().alert();
  }
}