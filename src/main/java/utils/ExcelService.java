package main.java.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ExcelService {

  private static ExcelService instance;

  private final String FILE_PATH = Paths.get("inputFolder").toAbsolutePath() + "\\skladkaTDmz.xls";
  private Workbook workbook;
  private Map<Integer, Map<String, String>> values;
  private String[] columnKeys = {"Kierunek", "Cel pordóży", "Data wyjazdy", "Data powrotu", "Liczba osób dorosłych",
          "Liczba dzieci", "Standardowa ochrona", "Pełny komfort", "Prestiżowa podróż"};

  private ExcelService() {
    initialize();
  }

  public static ExcelService getExcelService() {
    if (instance == null) {
      instance = new ExcelService();
    }
    return instance;
  }

  private void initialize() {
    Row row;
    Map<String, String> rowMap;
    try {
      workbook = WorkbookFactory.create(new File(FILE_PATH));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Sheet sheet = workbook.getSheetAt(0);
    values = new HashMap<>();

    for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      row = sheet.getRow(rowIndex);
      rowMap = new HashMap<>();
      for (int cellIndex = 1; cellIndex < 10; cellIndex++) {
        try {
          rowMap.put(columnKeys[cellIndex - 1], String.valueOf(row.getCell(cellIndex).getNumericCellValue()));
        } catch (IllegalStateException e) {
          rowMap.put(columnKeys[cellIndex - 1], row.getCell(cellIndex).getStringCellValue());
        }
      }
      values.put(rowIndex - 1, rowMap);
    }
  }

  public String getValue(int row, String columnName) {
    return values.get(row).get(columnName);
  }
}