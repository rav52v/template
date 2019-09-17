package main.java.runnable;

import main.java.functions.BrowserFunctions;
import main.java.poms.SamplePage;
import main.java.tools.ScreenRecorderService;
import main.java.utils.Driver;
import main.java.utils.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static main.java.functions.BrowserFunctions.cleanApps;
import static main.java.utils.ConfigService.getConfigService;

public class App {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    Logger log = LogManager.getLogger();
    int status = 0;
    if (getConfigService().getBooleanProperty("general.close_drivers_before_start")) cleanApps();
    try {
      Gui.getInstance().openJPanel();

      new SamplePage();


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