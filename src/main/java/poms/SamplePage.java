package main.java.poms;

import main.java.utils.Gui;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SamplePage extends PageBase {

  @FindBy(css = "sample path")
  private List<WebElement> sampleList;

  @FindBy(css = ".dupa")
  private WebElement element;


  private By sampleBy = By.cssSelector("span.actor-name");
  private By anotherBy = By.xpath("../div/a/figure/div/div/div");


  public SamplePage() {
    browser.openPage(Gui.getInstance().getSearchLinkAddress());
    testTimeouts(2);
    testTimeouts(3);
    testTimeouts(4);
    browser.closeDriver(0);
  }

  private void testTimeouts(int time) {
    long startTime = System.currentTimeMillis();
    try {
      new WebDriverWait(action.getWebDriver(), time)
              .until(ExpectedConditions.invisibilityOf(element));
    } catch (TimeoutException e) {
      System.out.printf("Score: %d --> Explicit: %d\n", (System.currentTimeMillis() - startTime), time);
    }
  }
}