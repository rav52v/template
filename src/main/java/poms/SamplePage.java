package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends PageBase {

  @FindBy(id = "gsc-i-id1")
  private WebElement element;


  public SamplePage() {
    browser.openPage("https://www.youtube.com/playlist?list=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj");



    browser.closeDriver(0);
  }
}