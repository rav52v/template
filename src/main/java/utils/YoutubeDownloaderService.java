package main.java.utils;

import com.github.axet.vget.VGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;

public class YoutubeDownloaderService {
  private static YoutubeDownloaderService instance;
  private Logger log = LogManager.getLogger();

  public static YoutubeDownloaderService getYoutubeService() {
    if (instance == null) instance = new YoutubeDownloaderService();
    return instance;
  }

  public void downloadVideo(String url, String path) {
    try {
      long startTime = System.currentTimeMillis();
      VGet v = new VGet(new URL(url), new File(path));
      String title = v.getVideo().getTitle();
      log.debug("Downloading started: {" + title + "}, link: {" + url + "}, destination: {" + path + "}");
      v.download();
      log.debug("Downloaded: {" + title + "}, it took: {" + (System.currentTimeMillis()-startTime) + "}");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}