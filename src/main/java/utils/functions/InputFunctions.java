package main.java.utils.functions;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

public class InputFunctions extends BaseFunction {

  /**
   * Use this method if input field requires click, before allows to send keys
   *
   * @param element input type element to click and to send keys
   * @param value   CharSequence values
   */
  public void clickAndSendKeys(WebElement element, CharSequence... value) {
    log.debug("Click ones and send keys {" + Arrays.toString(value)
            + "} to element {" + getElementInfo(element) + "}");

    element.clear();
    new Actions(driver.getDriver()).moveToElement(element).click().sendKeys(value).perform();
  }

  /**
   * @param elementToClick    element to click
   * @param elementToSendKeys input element to send keys
   * @param value             CharSequence values
   */
  public void clickElementAndSendKeysToAnother(WebElement elementToClick, WebElement elementToSendKeys, CharSequence... value) {
    log.debug("Click ones element {" + getElementInfo(elementToClick) + "} and send keys {" + Arrays.toString(value)
            + "} to element {" + getElementInfo(elementToSendKeys) + "}");

    new Actions(driver.getDriver()).moveToElement(elementToClick).click().sendKeys(value).perform();
    elementToSendKeys.clear();
    new Actions(driver.getDriver()).moveToElement(elementToSendKeys).sendKeys(value).perform();
  }

  public void sendKeysToElement(WebElement element, CharSequence... value) {
    log.debug("Send keys {" + Arrays.toString(value) + "} to element {"
            + getElementInfo(element) + "}");

    element.clear();
    element.sendKeys(value);
//        new Actions(driver.getDriver()).moveToElement(element).sendKeys(value).perform();
  }

  public void sendKeys(CharSequence... value) {
    log.debug("Send keys {" + Arrays.toString(value) + "}");

    new Actions(driver.getDriver()).sendKeys(value).perform();
  }

  protected void selectByVisibleTextWithRegex(WebElement selectElement, String regex) {
    Select select = new Select(selectElement);
    List<WebElement> options = select.getOptions();
    for (WebElement option : options) {
      if (option.getText().matches(regex)) {
        select.selectByVisibleText(option.getText());
        log.debug("Option selected by visible text: {" + option.getText() + "}");
      }
    }
  }

  protected void selectByValue(WebElement selectElement, String value) {
    try {
      Select select = new Select(selectElement);
      select.selectByValue(value);
      log.debug("Option selected by value: {" + value + "}");
    } catch (NoSuchElementException e) {
      log.error(e.toString());
      throw new RuntimeException(e.toString());
    }
  }

  protected void selectByIndex(WebElement selectElement, int index) {
    try {
      Select select = new Select(selectElement);
      select.deselectByIndex(index);
      log.debug("Option selected by index: {" + index + "}");
    } catch (NoSuchElementException e) {
      log.error(e.toString());
      throw new RuntimeException(e.toString());
    }
  }
}
