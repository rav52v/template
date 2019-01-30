package main.java.utils;

import main.java.tools.ConfigurationParser;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static Map <Integer, WebDriver> driverMap = new HashMap<>();
    private int key;

    private String path = Paths.get("src", "main", "resources").toAbsolutePath().toString();
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    public WebDriver getDriver() {
        if (driverMap.get(key) == null ) {
            LogManager.getLogger(this).info("Opening browser in {" + (Gui.getInstance().isHeadless() ? "headless" : "normal") + "} mode.");
            setProperties();
            driverMap.get(key).manage().timeouts().implicitlyWait(ConfigurationParser.getInstance().getImplicitlyWaitTime(), TimeUnit.SECONDS);
            driverMap.get(key).get(ConfigurationParser.getInstance().getLinkAddress());
        }
        return driverMap.get(key);
    }

    private void closeDriver() {
        driverMap.get(key).quit();
        driverMap.put(key, null);
    }

    public void afterTest(int sleepAfter) {
        try {
            Thread.sleep(sleepAfter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeDriver();
    }

    private void setProperties() {
        if (PLATFORM.startsWith("win"))
            System.setProperty("webdriver.chrome.driver", path + "/chromedriver.exe");
        else if (PLATFORM.startsWith("mac"))
            System.setProperty("webdriver.chrome.driver", path + "/chromedriver");
//        TODO: implement for linux

        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--disable-infobars");

        boolean headless = Gui.getInstance().isHeadless();
        chromeOptions.setHeadless(headless);
        if (headless) {
            chromeOptions.addArguments("--window-size=2000,4000");
            chromeOptions.addArguments("--disable-gpu");
        } else
            chromeOptions.addArguments("--start-maximized");

        key = new Random().nextInt(9999);
        driverMap.put(key, new ChromeDriver(chromeOptions));
    }
}