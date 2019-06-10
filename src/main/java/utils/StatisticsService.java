package main.java.utils;

import main.java.enums.Packages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

public class StatisticsService {

  private static StatisticsService instance;
  private Logger log = LogManager.getLogger();

  public static StatisticsService getStatisticsService() {
    if (instance == null) instance = new StatisticsService();
    return instance;
  }

  private StatisticsService() {
    if (new File(Packages.STATISTICS_FOLDER.getPackagePath()).mkdirs())
      log.debug("Created directory {" + Packages.STATISTICS_FOLDER.getPackagePath() + "}");
  }

  public void logPageLoadTime(String link, String value) {
    String fileName = "page_load_times";
    String regex = "((https?)?:[/]{2}(www[.])?)|([.]((pl)|(com)|(org)|(de)|(uk)|(info)|(ru)|(nl)|(cn)|(eu)|(gb)|(tv)|(jp)|(hk)|(es)|(it)|(fr))[/].*)";

    if (!Packages.STATISTICS_FOLDER.isFileInDirectory(fileName + ".xlsx"))
      ExcelService.getExcelService().createTable(
              new ArrayList<>(), new ArrayList<>(), fileName, Packages.STATISTICS_FOLDER);

    if (!ExcelService.getExcelService().isColumnCreated(link, fileName, Packages.STATISTICS_FOLDER)) {
      ExcelService.getExcelService().addColumn(link, fileName, Packages.STATISTICS_FOLDER);
      log.debug("Statistics - " + fileName + ".xlsx: created new column: " + link);
    }

    ExcelService.getExcelService().addValueInFirstEmptyCellOfColumn(
            link.replaceAll(regex, ".*"), value, fileName, Packages.STATISTICS_FOLDER);
    log.debug("Statistics - " + fileName + ".xlsx: added new value: " + value + " in column " + link);
  }


//  public static void main(String[] args) {
//    for (int i = 0; i < 5; i++) {
//      StatisticsService.getStatisticsService().logPageLoadTime("https://www.google.com/", String.valueOf(new Generators().randomFromRange(2000, 4000)));
//    }
//
//    for (int i = 0; i < 5; i++) {
//      StatisticsService.getStatisticsService().logPageLoadTime("https://www.youtube.com/", String.valueOf(new Generators().randomFromRange(2000, 4000)));
//    }
//
//    for (int i = 0; i < 5; i++) {
//      StatisticsService.getStatisticsService().logPageLoadTime("https://www.allegro.pl/", String.valueOf(new Generators().randomFromRange(2000, 4000)));
//    }
//  }


}
