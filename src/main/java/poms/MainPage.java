package main.java.poms;

import main.java.utils.Gui;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class MainPage extends PageBase {
  @FindBy(css = "a")
  private List<WebElement> sample1;

  @FindBy(css = "#ctl00_lnkCopyright")
  private WebElement sample2;

  @FindBy(css = "label[for='filestyle-0']")
  private WebElement sample3;

  @FindBy(id = "filestyle-0")
  private WebElement sample4;

  private By cloud = By.cssSelector("._5v-0._53im");

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
    browser.openPage(Gui.getInstance().getSearchLinkAddress());
    browser.sleeper(2000);
//        action.executeJavaScriptExecutor("arguments[0].removeAttribute('disabled');", sample4);
    input.sendKeysToElement(sample4, "C:\\Users\\p_florys\\Idea Workspace\\PageObjectModelBase\\src\\main\\resources\\config.properties");
  }


}