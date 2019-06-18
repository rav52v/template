package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class YoutubePlaylist extends PageBase {

  @FindBy(css = "div#contents>ytd-playlist-video-renderer")
  private List<WebElement> videos;

  @FindBy(css = ".dupa")
  private WebElement element;


  private By linkElement = By.cssSelector("div#content > a");
  private By anotherBy = By.xpath("../div/a/figure/div/div/div");

  private List<String> links;


  public YoutubePlaylist() {
    getLinks();
    saveLinks();
  }

  private void getLinks() {
    browser.openPage("https://www.youtube.com/playlist?list=PLSCChOT7N7PMkRQtlKaSwmn2V0RyChBPB");
    browser.scrollDownRefreshingPageTillEnd(videos);
    log.info("Playlist size: " + videos.size());
  }

  private void saveLinks() {
    links = new ArrayList<>();
    for (WebElement video : videos){
      String link = video.findElement(linkElement).getAttribute("href").replaceAll("&list=.+", "");
      log.debug(link);
      links.add(link);
    }
    log.info("Links amount: " + links.size());
  }
}