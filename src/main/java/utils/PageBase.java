package main.java.utils;

import main.java.functions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public abstract class PageBase {
  protected BrowserFunctions browser;
  protected CheckFunctions check;
  protected ClickFunctions click;
  protected FileFunctions file;
  protected InputFunctions input;
  protected ActionFunctions action;
  protected GetFunctions get;
  protected Logger log;

  protected PageBase() {
    browser = new BrowserFunctions();
    check = new CheckFunctions();
    click = new ClickFunctions();
    file = new FileFunctions();
    input = new InputFunctions();
    action = new ActionFunctions();
    get = new GetFunctions();
    log = LogManager.getLogger();

    PageFactory.initElements(Driver.getDriverInstance().getDriver(), this);
  }
}
