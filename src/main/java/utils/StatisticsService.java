package main.java.utils;

import main.java.enums.Packages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

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
      log.debug("Statistics - {" + fileName + ".xlsx}: created new column: {" + link + "}");
    }

    ExcelService.getExcelService().addValueInFirstEmptyCellOfColumn(
            link.replaceAll(regex, ".*"), value, fileName, Packages.STATISTICS_FOLDER);
    log.debug("Statistics - {" + fileName + ".xlsx}: added new value: {" + value + "} in column {" + link + "}");
  }

  public long getAvgNumericValueFromColumn(String regexColumnName, String fileName) {
    ArrayList<Long> sorted = getSortedArrayFromNumericColumn(regexColumnName, fileName);
    long avg = sorted.stream().mapToLong(e -> e).sum() / sorted.size();
    long min = sorted.stream().mapToLong(e -> e).min().getAsLong();
    long max = sorted.stream().mapToLong(e -> e).max().getAsLong();
    log.debug(String.format("Values: {min: %d max: %d avg: %d} for column: {%s}", min, max, avg, regexColumnName));
    return avg;
  }

  private ArrayList<Long> getSortedArrayFromNumericColumn(String regexColumnName, String fileName) {
    ArrayList<Long> sorted = new ArrayList<>();
    ExcelService.getExcelService().getDataFromColumn(
            regexColumnName, fileName, Packages.STATISTICS_FOLDER).forEach(e -> sorted.add(Long.parseLong(e)));
    Collections.sort(sorted);
    return sorted;
  }
}