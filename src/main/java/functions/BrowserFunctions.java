package main.java.functions;

import main.java.utils.StatisticsService;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class BrowserFunctions extends BaseFunction {

  private String mainWindowHandle;

  /**
   * Switches to window, which is not main window
   * This method supports only one additional window
   */
  public void switchToSecondTab() {
    log.debug("Switch to second tab.");
    if (mainWindowHandle == null) mainWindowHandle = driver.getMainWindowHandle();
    for (String winHandle : driver.getDriver().getWindowHandles())
      if (!winHandle.equals(mainWindowHandle)) driver.setDriver(driver.getDriver().switchTo().window(winHandle));
  }

  /**
   * Switches back to main tab
   */
  public void switchToMainTab() {
    log.debug("Switch to main tab.");
    driver.setDriver(driver.getDriver().switchTo().window(mainWindowHandle));
  }

  /**
   * Closes focused tab
   */
  public void closeTab() {
    log.debug("Close tab.");
    driver.getDriver().close();
  }

  public void switchToIFrame(Object frame) {
    log.debug("Switch to iframe: {" + frame + "}.");
    driver.switchToIframe(frame);
  }

  public void switchBackFromIFrame() {
    log.debug("Switch to default content.");
    driver.getDriver().switchTo().defaultContent();
  }

  /**
   * Opens new tab and focus
   */
  public void openNewTab() {
    log.debug("Open new tab.");
    ((JavascriptExecutor) driver.getDriver()).executeScript("window.open();");
    switchToSecondTab();
  }

  public void openPage(String linkAddress) {
    long startTime = System.currentTimeMillis();
    log.debug("Loading page (expected) {" + linkAddress + "}.");
    try {
      driver.getDriver().get(linkAddress);
      log.debug("Page loaded {" + driver.getDriver().getCurrentUrl() + "}, operation took {"
              + getPassedTimeInMillis(startTime) + "}.");
    } catch (TimeoutException e) {
      log.debug("Page was loading too long, page load time is: {"
              + getConfigService().getLongProperty("general.pageLoadTime") + "}.");
    }
    StatisticsService.getStatisticsService()
            .logPageLoadTime(linkAddress, String.valueOf(getPassedTimeInMillis(startTime)));
    waitForPageLoading();
  }

  public void closeDriver(int... delayMillis) {
    mainWindowHandle = null;
    driver.afterTest(delayMillis);
  }

  public void scrollPageBy(int widthToScroll, int heightToScroll) {
    ((JavascriptExecutor) driver.getDriver())
            .executeScript("window.scrollBy(arguments[0],arguments[1])", widthToScroll, heightToScroll);
  }

  /**
   * Scroll to the end of the page
   */
  public void scrollBottom() {
    ((JavascriptExecutor) driver.getDriver())
            .executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
  }

  /**
   * @param speed     more = faster   (20-slow / 1000-fast)
   * @param intervals breaks between each scroll in milliseconds
   */
  public void scrollBottomSlowMotion(int speed, int... intervals) {
    JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
    long pageY = (long) js.executeScript("return Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight)");
    long windowInnerY = (long) js.executeScript("return window.innerHeight");

    for (long i = windowInnerY; i <= pageY; ) {
      js.executeScript("window.scrollBy(0,arguments[0])", speed);
      if (intervals.length != 0) sleeper(intervals[0]);
      i += speed;
      if (i >= pageY) sleeper(1500);
      pageY = (long) js.executeScript("return Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight)");
    }
  }

  public void scrollIntoView(WebElement element) {
    ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].scrollIntoView()", element);
  }

  /**
   * @param element   WebElement, we want to see in view port
   * @param alignment One of "start", "center", "end", or "nearest"
   * @param smooth    Set 'true' for slow animation
   */
  public void scrollIntoView(WebElement element, String alignment, boolean... smooth) {
    ((JavascriptExecutor) driver.getDriver()).executeScript(
            "arguments[0].scrollIntoView({behavior: \""
                    + (smooth.length == 0 ? "auto" : (smooth[0] ? "smooth" : "auto"))
                    + "\", block: \"" + alignment + "\"})", element);
  }

  public void refreshPage() {
    driver.getDriver().navigate().refresh();
  }

  /**
   * @param elementList list of WebElements, which will be increase after scroll down.
   */
  public void scrollDownRefreshingPageTillEnd(List<WebElement> elementList) {
    long startTime = System.currentTimeMillis();
    int refreshingListSize, refreshingListAfterSize;
    do {
      refreshingListSize = elementList.size();
      new Actions(driver.getDriver()).sendKeys(Keys.END).perform();
      waitForPageLoading();
      refreshingListAfterSize = elementList.size();
    }
    while (refreshingListAfterSize > refreshingListSize);
    log.debug("Found {" + elementList.size() + " elements}, operation took {" + getPassedTimeInMillis(startTime) + "}.");
  }

  public Alert getAlertControl() {
    return driver.getDriver().switchTo().alert();
  }

  public void acceptAlert() {
    changeImplicitlyWaitTime(0);
    int i = 0;
    while (i++ < 10) {
      try {
        new WebDriverWait(driver.getDriver(), 5).until(ExpectedConditions.alertIsPresent()).accept();
        log.debug("Accepted alert.");
        break;
      } catch (NoAlertPresentException e) {
        log.debug("Waiting for alert, attempt {" + i + "/10}.");
      } finally {
        turnOnImplicitlyWaitTime();
      }
    }
  }

  public static void cleanApps() {
    String line;
    try {
      Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
        if (line.contains("chromedriver.exe")) {
          Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        } else if (line.contains("chrome.exe")) {
          Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}