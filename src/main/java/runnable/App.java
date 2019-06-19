package main.java.runnable;

import main.java.enums.Packages;
import main.java.poms.MainPage;
import main.java.poms.YoutubePlaylist;
import main.java.tools.ScreenRecorderService;
import main.java.utils.StatisticsService;
import main.java.utils.YoutubeDownloaderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static main.java.utils.ConfigService.getConfigService;

public class App {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    Logger log = LogManager.getLogger();

//    Gui.getInstance().openJPanel();


//    YoutubePlaylist yt = new YoutubePlaylist();
//    List<String> links = yt.getLinks();
//    StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.youtube.com/?gl=PL&hl=pl", "page_load_times");
//    yt.killBrowser();
//    YoutubeDownloaderService.getYoutubeService().downloadVideo("https://www.youtube.com/watch?v=HkuKHwetV6Q", Packages.OUTPUT_FOLDER.getPackagePath());


    new MainPage();


    log.info("Program has finished. Operation took {" + calculatePastTime(start) + "}.");
//    Gui.getInstance().showLogInfo();

    if (getConfigService().getBooleanProperty("general.recordScreen"))
      ScreenRecorderService.getScreenRecorder().stopRecordingScreen();

    System.exit(0);
  }

  private static String calculatePastTime(long start) {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    return df.format(new Date(System.currentTimeMillis() - start));
  }
}