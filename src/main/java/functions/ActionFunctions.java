package main.java.functions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionFunctions extends BaseFunction {

  public void moveToElement(WebElement element) {
    log.debug("Move to element: {" + getElInfo(element) + "}.");
    new Actions(driver.getDriver()).moveToElement(element).perform();
  }

  public void changeElementAttributeValue(WebElement element, String attributeName, String value) {
    ((JavascriptExecutor) driver.getDriver()).executeScript(
            "arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, value);
  }

  public Actions getActions() {
    return new Actions(driver.getDriver());
  }

  public void executeJavaScriptExecutor(String script) {
    ((JavascriptExecutor) driver.getDriver()).executeScript(script);
  }

  public void executeJavaScriptExecutor(String script, Object... args) {
    ((JavascriptExecutor) driver.getDriver()).executeScript(script, args);
  }
}