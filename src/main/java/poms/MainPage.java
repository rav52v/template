package main.java.poms;

import main.java.utils.Gui;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
    secondMethod();
  }


  private void firstMethod() {
    browser.openPage(Gui.getInstance().getSearchLinkAddress());
    browser.openNewTab();
    browser.switchToSecondTab();
    browser.openPage("https://www.youtube.com/?hl=pl&gl=PL");
    sleeper(1000);
    browser.switchToMainTab();
    browser.openPage("http://www.google.pl/");
    browser.switchToSecondTab();
    browser.closeTab();
    browser.switchToMainTab();
    browser.closeDriver(2000);
  }

  private void secondMethod() {
    browser.openPage(Gui.getInstance().getSearchLinkAddress());
    sleeper(2000);
//        action.executeJavaScriptExecutor("arguments[0].removeAttribute('disabled');", sample4);
    input.sendKeysToElement(sample4, "C:\\Users\\p_florys\\Idea Workspace\\PageObjectModelBase\\src\\main\\resources\\config.properties");
  }


}