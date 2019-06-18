package main.java.runnable;

import main.java.poms.YoutubePlaylist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class App {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    Logger log = LogManager.getLogger();

//    Gui.getInstance().openJPanel();


    YoutubePlaylist yt = new YoutubePlaylist();
    List<String> links = yt.getLinks();


    
    yt.killBrowser();

//    new MainPage();


    log.info("Program has finished. Operation took {" + calculatePastTime(start) + "}.");
//    Gui.getInstance().showLogInfo();
  }

  private static String calculatePastTime(long start) {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    return df.format(new Date(System.currentTimeMillis() - start));
  }
}