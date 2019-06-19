package main.java.poms;

import main.java.utils.PageBase;
import main.java.utils.StatisticsService;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class MainPage extends PageBase {
  @FindBy(css = "#tables-slider-1 a")
  private List<WebElement> sample1;

  @FindBy(css = "body > div.mui-container.tp-select-readings.tp-home > div:nth-child(6) > div > a > img")
  private WebElement sample2;

  @FindBy(css = "body > div.tp-search-widget > div > h4")
  private WebElement sample3;

  @FindBy(css = "body.graph:nth-child(2) div:nth-child(3) span:nth-child(1) > div.gotit:nth-child(6)")
  private WebElement sample4;

  private By cloud = By.cssSelector("#latest-ul > li.latest-doodle.on > div > div > a > img");

  public MainPage() {
    for (int i = 1; i <= 1; i++) {
      System.out.println(i + "/1 started.");
      thirdMethod();
    }
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.google.pl/", "page_load_times");
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.udemy.com/java-tutorial/", "page_load_times");
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://allegro.pl/", "page_load_times");
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://pl.aliexpress.com/", "page_load_times");
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.olx.pl/", "page_load_times");
    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.t-mobile.pl/", "page_load_times");
    browser.closeDriver(0);
  }

  private void firstMethod() {
    browser.openPage(getConfigService().getStringProperty("general.linkAddress"));
    browser.openNewTab();
    browser.switchToSecondTab();
    browser.openPage("https://www.google.pl/");
    browser.sleeper(200);
    browser.switchToMainTab();
    browser.openPage("https://www.tutorialspoint.com/");
    browser.switchToSecondTab();
    browser.closeTab();
    browser.switchToMainTab();
    browser.closeDriver(0);
  }

  private void secondMethod() {
    browser.openPage("https://www.tutorialspoint.com/");
    browser.scrollPageBy(0, 2000);
    browser.closeDriver(3000);
  }

  private void thirdMethod() {
    browser.openPage("https://www.google.pl/");
//    browser.openPage("https://www.udemy.com/java-tutorial/");
//    browser.openPage("https://allegro.pl/");
//    browser.openPage("https://pl.aliexpress.com/");
//    browser.openPage("https://www.olx.pl/");
    browser.openPage("https://www.t-mobile.pl/");
  }
}