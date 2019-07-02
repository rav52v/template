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

  private By linkElement = By.cssSelector("div#content > a");

  public YoutubePlaylist() {
  }

  public List<String> getLinks() {
    browser.openPage("https://www.youtube.com/playlist?list=PLSCChOT7N7PMkRQtlKaSwmn2V0RyChBPB");
    browser.scrollDownRefreshingPageTillEnd(videos);
    log.info("Playlist size: " + videos.size());
    List<String> links = new ArrayList<>();
    for (WebElement video : videos)
      links.add(video.findElement(linkElement).getAttribute("href").replaceAll("&list=.+", ""));

    final String[] toFile = {""};
    links.forEach(e -> toFile[0] += e.concat(System.lineSeparator()));
    file.saveTextToFile(toFile[0].replaceAll("[\\s]+$", ""), "YouTube links", false);
    log.info("Number of links: " + links.size());
    return links;
  }

  public void killBrowser() {
    browser.closeDriver(0);
  }
}