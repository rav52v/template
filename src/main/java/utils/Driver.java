package main.java.utils;

import main.java.tools.ConfigurationParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static WebDriver webDriverInstance;
    private String path = Paths.get("src", "main", "resources").toAbsolutePath().toString();
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    public WebDriver getDriver() {
        if (webDriverInstance == null) {
            setProperties();
            webDriverInstance.manage().timeouts().implicitlyWait(ConfigurationParser.getInstance().getImplicitlyWaitTime(), TimeUnit.SECONDS);
            webDriverInstance.get(ConfigurationParser.getInstance().getLinkAddress());
        }
        return webDriverInstance;
    }

    private void closeDriver() {
        webDriverInstance.quit();
        webDriverInstance = null;
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

        webDriverInstance = new ChromeDriver(chromeOptions);
    }
}