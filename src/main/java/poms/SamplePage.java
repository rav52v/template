package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends PageBase {

  @FindBy(css = ".hamburger.hamburger--collapse")
  private WebElement element1;

  @FindBy(css = "#wrapcentre > div.middle > div > table:nth-child(1) > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(4) > td:nth-child(2) > a")
  private WebElement element2;




  public SamplePage() {
    browser.openPage("https://www.fly4free.pl/forum/");

    click.clickOn(element1);

    browser.closeDriver(2000);
  }
}