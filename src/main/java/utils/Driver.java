package main.java.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
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
  private static Driver instance;
  private static Map<Integer, WebDriver> driverMap = new HashMap<>();
  private static int key;
  private boolean headless = isGuiCreated() ?
          Gui.getInstance().isHeadless() : getConfigService().getBooleanProperty("general.headless");

  private Driver(){}

  public static Driver getDriverInstance() {
    if (instance == null) instance = new Driver();
    return instance;
  }

  public WebDriver getDriver() {
    if (driverMap.isEmpty()) {
      LogManager.getLogger().info("Opening browser in {" + (headless ? "headless" : "normal") + "} mode.");
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

  private void closeDriver() {
    driverMap.get(key).quit();
    driverMap.clear();
    LogManager.getLogger().info("The browser has been closed.");
  }

  public void afterTest(int sleepAfter) {
    try {
      Thread.sleep(sleepAfter);
    } catch (InterruptedException ignored) {
    }
    closeDriver();
  }

  private ChromeOptions setChromeOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--disable-infobars");
    chromeOptions.setHeadless(headless);
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