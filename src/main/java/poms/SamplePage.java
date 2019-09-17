package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends PageBase {

  @FindBy(css = "div.code-example:nth-child(8) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)")
  private WebElement element;


  public SamplePage() {
    browser.openPage("https://javascript.info/alert-prompt-confirm");

    click.clickOn(element);

    browser.closeDriver(500);
  }
}