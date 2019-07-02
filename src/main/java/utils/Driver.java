package main.java.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.enums.Packages;
import main.java.tools.ScreenRecorderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static main.java.utils.ConfigService.getConfigService;
import static main.java.utils.Gui.isGuiCreated;

public class Driver {
  private Logger log = LogManager.getLogger();
  private static Driver instance;
  private static Map<Integer, WebDriver> driverMap = new HashMap<>();
  private int key;
  private boolean headless = isGuiCreated() ?
          Gui.getInstance().isHeadless() : getConfigService().getBooleanProperty("general.headless");

  private Driver() {
  }

  public static Driver getDriverInstance() {
    if (instance == null) {
      instance = new Driver();
      if (getConfigService().getBooleanProperty("general.recordScreen"))
        ScreenRecorderService.getScreenRecorder().startRecordingScreen();
    }
    return instance;
  }

  public WebDriver getDriver() {
    if (driverMap.isEmpty()) {
      log.info("Opening browser in {" + (headless ? "headless" : "normal") + "} mode.");
      setProperties();
      driverMap.get(key).manage().timeouts().implicitlyWait(getConfigService()
              .getLongProperty("general.implicitlyWaitTime"), TimeUnit.SECONDS);
      driverMap.get(key).get(getConfigService().getStringProperty("general.linkAddress"));
    }
    return driverMap.get(key);
  }

  public String getMainWindowHandle() {
    return driverMap.get(key).getWindowHandle();
  }

  public void setDriver(WebDriver newDriver) {
    driverMap.put(key, newDriver);
  }

  public void switchToIframe(Object frame) {
    if (frame instanceof Integer) driverMap.put(key, driverMap.get(key).switchTo().frame((int) frame));
    else driverMap.put(key, driverMap.get(key).switchTo().frame((String) frame));
  }

  private void closeDriver() {
    if (!driverMap.isEmpty()) {
      driverMap.get(key).quit();
      driverMap.clear();
      log.debug("The browser has been closed.");
    } else log.debug("Don't need to close driver, it is already closed.");
  }

  public void afterTest(int...sleepAfter) {
    if (sleepAfter.length > 0){
      try {
        Thread.sleep(sleepAfter[0]);
      } catch (InterruptedException ignored) {
      }
    }
    closeDriver();
  }

  /**
   * -disable infobar
   * -set if headless
   * -set download directory default path
   * -set start maximized or window size in headless mode
   *
   * @return ChromeOptions
   */
  private ChromeOptions setChromeOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--disable-infobars");
    chromeOptions.setHeadless(headless);
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", Packages.DOWNLOAD_PACKAGE.getPackagePath());
    chromeOptions.setExperimentalOption("prefs", prefs);
    if (headless) {
      chromeOptions.addArguments("--window-size=1500,4000");
      chromeOptions.addArguments("--disable-gpu");
    } else chromeOptions.addArguments("--start-maximized");

    return chromeOptions;
  }

  private void setProperties() {
    WebDriverManager.chromedriver().setup();
    key = new Random().nextInt(Integer.MAX_VALUE);
    driverMap.put(key, new ChromeDriver(setChromeOptions()));
  }
}