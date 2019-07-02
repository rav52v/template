package main.java.poms;

import main.java.utils.Driver;
import main.java.utils.PageBase;
import main.java.utils.StatisticsService;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static main.java.utils.ConfigService.getConfigService;

public class YoutubeDownloadPage extends PageBase {
  @FindBy(css = "div#result table.table.table-bordered > tbody > tr:first-child > td:last-of-type > a")
  private WebElement pobieranie;

  private By directLink = By.cssSelector("#process-result > div:last-child > a");

  public YoutubeDownloadPage(List<String> links) {
    downloadVideo(links);
  }

  private void downloadVideo(List<String> links) {
    for (int i = 0; i < links.size(); i++) {
      /* https://sconverter.com/pl/ */
      /* https://y2mate.com/ */
      browser.openPage(links.get(i).replaceAll("youtube", "youtu2"));

      try {
        new WebDriverWait(Driver.getDriverInstance().getDriver(), 20).until(ExpectedConditions.elementToBeClickable(pobieranie));
        pobieranie.click();
      } catch (Exception e) {
        log.error("Failed to download: " + (i + 1) + " {" + links.get(i) + "}");
        continue;
      }

      try {
        new WebDriverWait(Driver.getDriverInstance().getDriver(), 40).until(ExpectedConditions.elementToBeClickable(directLink));
        WebElement element = Driver.getDriverInstance().getDriver().findElement(directLink);
        click.clickOn(element);
        log.info("Success: " + (i + 1) + " {" + links.get(i) + "}");
        log.debug("Download link: " + (i + 1) + " {" + element.getAttribute("href") + "}");
      } catch (Exception e) {
        log.error("Failed to download: " + (i + 1) + " {" + links.get(i) + "}");
        continue;
      }
      browser.sleeper(1000);
    }
  }
}