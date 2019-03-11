package main.java.utils;

import main.java.utils.functions.*;
import org.openqa.selenium.support.PageFactory;

public abstract class PageBase {
  protected BrowserFunctions browser;
  protected CheckFunctions check;
  protected ClickFunctions click;
  protected FileFunctions file;
  protected InputFunctions input;
  protected ActionFunctions action;
  protected GetFunctions get;


  protected PageBase() {
    browser = new BrowserFunctions();
    check = new CheckFunctions();
    click = new ClickFunctions();
    file = new FileFunctions();
    input = new InputFunctions();
    action = new ActionFunctions();
    get = new GetFunctions();

    PageFactory.initElements(new Driver().getDriver(), this);
  }

  protected void sleeper(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
