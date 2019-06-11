package main.java.utils;

import main.java.enums.Packages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

  public long getAvgNumericValueFromColumn(String regexColumnName, String fileName){
    List<String> values = ExcelService.getExcelService()
            .getDataFromColumn(regexColumnName, fileName, Packages.STATISTICS_FOLDER);
    return values.stream().mapToLong(Long::parseLong).sum() / values.size();
  }


//  public static void main(String[] args) {
//    long start = System.currentTimeMillis();
//    System.out.println("oracle: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn(".*oracle.*", "page_load_times"));
//    System.out.println("google: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn(".*google.*", "page_load_times"));
//    System.out.println("udemy: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn(".*udemy.*", "page_load_times"));
//    System.out.println("w3schools: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn(".*w3schools.*", "page_load_times"));
//    System.out.println("tutorialspoint: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn(".*tutorialspoint.*", "page_load_times"));
//    System.out.println("javapoint: " + StatisticsService.getStatisticsService().getAvgNumericValueFromColumn("https://www.javatpoint.com/java-tutorial", "page_load_times"));
//    System.out.println();
//    System.out.println("time: " + (System.currentTimeMillis()-start));
//  }
}
