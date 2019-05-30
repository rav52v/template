package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class MainPage extends PageBase {
  @FindBy(css = "#tables-slider-1 a")
  private List<WebElement> sample1;

  @FindBy(css = "#tsf > div:nth-child(2) > div > div.FPdoLc.VlcLAe > center > input[type=submit]:nth-child(2)")
  private WebElement sample2;

  @FindBy(css = "#latest-ul > li.latest-doodle.on > div > div > a > img")
  private WebElement sample3;

  @FindBy(id = "filestyle-0")
  private WebElement sample4;

  private By cloud = By.cssSelector("#latest-ul > li.latest-doodle.on > div > div > a > img");

  public MainPage() {
    firstMethod();
  }


  private void firstMethod() {
    browser.openPage(getConfigService().getStringProperty("general.linkAddress"));
    browser.openNewTab();
    browser.switchToSecondTab();
    browser.openPage("https://www.youtube.com/?hl=pl&gl=PL");
    browser.sleeper(500);
    browser.switchToMainTab();
    browser.openPage("http://www.google.pl/");
    browser.switchToSecondTab();
    browser.closeTab();
    browser.switchToMainTab();
    browser.closeDriver(1000);
  }

  private void secondMethod() {

  }
}