package main.java.utils;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ExcelService {

  private static ExcelService instance;

  private final String OUTPUT_FILE_PATH = Paths.get("outputFolder").toAbsolutePath() + "\\";
  private XSSFWorkbook workbook;
  private XSSFRow row;
  private XSSFSheet sheet;

  private ExcelService() {
  }

  public static ExcelService getExcelService() {
    if (instance == null) {
      instance = new ExcelService();
    }
    return instance;
  }

  public void createTable(List<String> columns, List<List<String>> values, String fileName) {
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet();
    row = sheet.createRow(0);
    XSSFCellStyle topCellStyle = workbook.createCellStyle();
    XSSFFont topFont = workbook.createFont();
    topFont.setBold(true);
    topFont.setFontHeight(15);
    topCellStyle.setAlignment(HorizontalAlignment.CENTER);
    topCellStyle.setFont(topFont);

    for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
      XSSFCell cell = row.createCell(columnIndex);
      cell.setCellStyle(topCellStyle);
      cell.setCellValue(columns.get(columnIndex));
    }

    for (int rowIndex = 1; rowIndex <= values.size(); rowIndex++) {
      row = sheet.createRow(rowIndex);
      for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellValue(values.get(rowIndex - 1).get(columnIndex));
      }
    }
    for (int i = 0; i < columns.size(); i++) sheet.autoSizeColumn(i);

    try {
      FileOutputStream out = new FileOutputStream(new File(OUTPUT_FILE_PATH + fileName + ".xlsx"));
      workbook.write(out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}