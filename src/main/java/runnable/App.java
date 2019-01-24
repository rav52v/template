package main.java.runnable;

import main.java.poms.MainPage;
import main.java.utils.Driver;
import main.java.utils.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class App {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Logger log = LogManager.getLogger(App.class);
        log.info("Log created.");

        log.info("Opening Gui.");
        Gui.getInstance().openJPanel();

        log.info("Opening browser in {" + (Gui.getInstance().isHeadless() ? "headless" : "normal") + "} mode.");
        Driver driver = new Driver();
        driver.beforeTest();
        //      TODO



        new MainPage();




        //      TODO
        driver.afterTest(0);
        log.info("The browser has been closed.");

        log.info("Program has finished. Operation took {" + calculatePastTime(start) + "}.");

        Gui.getInstance().showLogInfo();
    }

    private static String calculatePastTime(long start) {
        long elapsed = System.currentTimeMillis() - start;
        DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins,' ss 'seconds'");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return df.format(new Date(elapsed));
    }
}