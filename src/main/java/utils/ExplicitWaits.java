package main.java.utils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExplicitWaits {
  public static ExpectedCondition<Boolean> textToBePresentInField(final WebElement element, final String text) {
    return new ExpectedCondition<>() {
      public Boolean apply(WebDriver driver) {
        try {
          String value = element.getText();
          value = value == null || value.isEmpty() ? element.getAttribute("value").trim() : value.trim();
          return value.equalsIgnoreCase(text);
        } catch (StaleElementReferenceException e) {
          return null;
        }
      }

      public String toString() {
        return String.format("text ('%s') to be present in element %s", text, element);
      }
    };
  }
}
