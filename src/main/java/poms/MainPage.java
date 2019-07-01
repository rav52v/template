package main.java.poms;

import main.java.utils.Driver;
import main.java.utils.ExplicitWaits;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class MainPage extends PageBase {
  @FindBy(css = "#tables-slider-1 a")
  private List<WebElement> sample1;

  @FindBy(css = "body > div.mui-container.tp-select-readings.tp-home > div:nth-child(6) > div > a > img")
  private WebElement sample2;

  @FindBy(css = "body > div.trytopnav > div > a.w3-button.w3-bar-item.topnav-icons.fa.fa-adjust")
  private WebElement sample3;

  @FindBy(css = "body > p:nth-child(2) > a > img")
  private WebElement sample4;

  @FindBy(css = "#main > input[type=text]:nth-child(16)")
  private WebElement sample5;

  private By cloud = By.cssSelector("#latest-ul > li.latest-doodle.on > div > div > a > img");

  public MainPage() {
    secondMethod();
  }

  private void firstMethod() {
    browser.openPage(getConfigService().getStringProperty("general.linkAddress"));
    browser.openNewTab();
    browser.switchToSecondTab();
    browser.openPage("https://www.w3schools.com/howto/tryit.asp?filename=tryhow_html_download_link2");
    browser.switchToIFrame("iframeResult");
    System.out.println(sample4.getAttribute("alt"));
    browser.switchBackFromIFrame();
    browser.switchToMainTab();
    browser.openPage("https://www.tutorialspoint.com/");
    browser.switchToSecondTab();
    browser.closeTab();
    browser.switchToMainTab();
    browser.closeDriver(0);
  }

  private void secondMethod() {
    browser.openPage("https://www.w3schools.com/html/html_form_input_types.asp");
    browser.scrollIntoView(sample5,"center", true);
    new WebDriverWait(Driver.getDriverInstance().getDriver(), 60)
            .until(ExplicitWaits.textToBePresentInField(sample5, "dupa"));
    browser.closeDriver();
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