package main.java.runnable;

import main.java.poms.YoutubeDownloadPage;
import main.java.poms.YoutubePlaylist;
import main.java.tools.ScreenRecorderService;
import main.java.utils.Driver;
import main.java.utils.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static main.java.utils.ConfigService.getConfigService;

public class YoutubeApp {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    Logger log = LogManager.getLogger();
    int status = 0;
    try {
      Gui.getInstance().openJPanel();


      YoutubePlaylist yt = new YoutubePlaylist();
      List<String> links = yt.getLinks();
//    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.youtube.com/?gl=PL&hl=pl", "page_load_times");
//    YoutubeDownloaderService.getYoutubeService().downloadVideo("https://www.youtube.com/watch?v=HkuKHwetV6Q", Packages.OUTPUT_PACKAGE.getPackagePath());



      new YoutubeDownloadPage(links);


    } catch (Exception e) {
      e.printStackTrace();
      log.error("Program crashed: " + e.getMessage());
      status--;
    } finally {
      if (getConfigService().getBooleanProperty("general.recordScreen"))
        ScreenRecorderService.getScreenRecorder().stopRecordingScreen();
      Driver.getDriverInstance().afterTest();
      log.info("Program has finished. Operation took {" + calculatePastTime(start) + "}.");
      Gui.getInstance().showLogInfo();
      System.exit(status);
    }
  }

  private static String calculatePastTime(long start) {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    return df.format(new Date(System.currentTimeMillis() - start));
  }
}