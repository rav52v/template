package main.java.tools;

import main.java.utils.Gui;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {

  private String[] columns = {"Column1", "Column2", "Column3", "Column4", "Column5", "Column6"};
  private final String FILE_NAME = new File("").toPath().toAbsolutePath().toString() + "/"
          + Gui.getInstance().getFileName() + ".xlsx";

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;

  //    update this with value:
  private int sumOfRows;


  public Excel() {
    initializeColumnsNames();
    fillSheet();
  }

  private void initializeColumnsNames() {
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet("Practice Java Excel");

    // Set columns width
    sheet.setColumnWidth(0, 5500);
    sheet.setColumnWidth(1, 5500);
    sheet.setColumnWidth(2, 16500);
    sheet.setColumnWidth(3, 8500);
    sheet.setColumnWidth(4, 5000);
    sheet.setColumnWidth(5, 15000);

    Row row = sheet.createRow(0);

    // Set headers style
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 14);
    headerFont.setColor(IndexedColors.RED.getIndex());
    CellStyle headerCellStyle = workbook.createCellStyle();
    headerCellStyle.setFont(headerFont);
    headerCellStyle.setLocked(true);

    // Create headers
    for (int i = 0; i < columns.length; i++) {
      Cell cell = row.createCell(i);
      cell.setCellValue(columns[i]);
      cell.setCellStyle(headerCellStyle);
    }

    try {
      FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
      workbook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fillSheet() {
    CellStyle backgroundStyle = workbook.createCellStyle();
    backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    // Add records
    for (int i = 1; i <= sumOfRows; i++) {
      Row row = sheet.createRow(i);
      for (int j = 0; j < columns.length; j++) {
        Cell cell = row.createCell(j);
        cell.setCellValue("SOME VALUES");
      }
    }

    try {
      FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
