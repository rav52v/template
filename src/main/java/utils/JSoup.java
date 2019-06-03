package main.java.utils;

import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSoup {

  private static JSoup instance;

  public static JSoup getJSoup() {
    if (instance == null) instance = new JSoup();
    return instance;
  }

  private JSoup() {
  }

  /**
   * @param url             html address
   * @param cssColumnTitles complete cssSelector
   * @param cssRows         complete cssSelector
   * @param cssRecordsInRow cssSelector inside cssRow
   * @param fileName        name of file to create
   */
  public void exportTableToExcel(String url, String cssColumnTitles, String cssRows, String cssRecordsInRow, String fileName) {
    Document doc = null;
    try {
      doc = Jsoup.connect(url).userAgent("ua").maxBodySize(999999999).get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<List<String>> values = new ArrayList<>();
    for (Element row : doc.select(cssRows)) values.add(new ArrayList<>(row.select(cssRecordsInRow).eachText()));
    ExcelService.getExcelService().createTable(doc.select(cssColumnTitles).eachText(), values, fileName);
    LogManager.getLogger().info("Exported rows: " + values.size());
  }
}