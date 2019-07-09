package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends PageBase {

  @FindBy(id = "gsc-i-id1")
  private WebElement element;


  public SamplePage() {
    browser.openPage("https://www.tutorialspoint.com/");

    input.sendKeysToElement(element, "dupa", Keys.valueOf("ENTER"));

    browser.closeDriver(1000);
  }
}