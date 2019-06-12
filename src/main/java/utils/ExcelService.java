package main.java.utils;

import main.java.enums.Packages;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {
  private static ExcelService instance;

  private XSSFWorkbook workbook;
  private XSSFRow row;
  private XSSFSheet sheet;

  private ExcelService() {
  }

  public static ExcelService getExcelService() {
    if (instance == null) instance = new ExcelService();
    return instance;
  }

  /**
   * @return Pre-defined cell style designed for first row - column names
   */
  private XSSFCellStyle getDefaultColumnNameStyle() {
    XSSFFont topFont = workbook.createFont();
    topFont.setBold(true);
    topFont.setFontHeight(15);
    XSSFCellStyle topCellStyle = workbook.createCellStyle();
    topCellStyle.setAlignment(HorizontalAlignment.CENTER);
    topCellStyle.setFont(topFont);
    return topCellStyle;
  }

  public void createTable(List<String> columns, List<List<String>> values, String fileName, Packages directory) {
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet();
    row = sheet.createRow(0);

    for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
      XSSFCell cell = row.createCell(columnIndex);
      cell.setCellStyle(getDefaultColumnNameStyle());
      cell.setCellValue(columns.get(columnIndex));
    }


    for (int rowIndex = 1; rowIndex <= values.size(); rowIndex++) {
      row = sheet.createRow(rowIndex);
      for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++)
        row.createCell(columnIndex).setCellValue(values.get(rowIndex - 1).get(columnIndex));
    }
    for (int i = 0; i < columns.size(); i++) sheet.autoSizeColumn(i);

    try {
      FileOutputStream out = new FileOutputStream(new File(directory.getPackagePath() + fileName + ".xlsx"));
      workbook.write(out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This function checks if column with given name exists
   *
   * @param regexColumnName name of new column we want to check if exist
   * @param fileName        e.g. my_file
   * @param directory       e.g. Packages.STATISTICS_FOLDER
   * @return
   */
  public boolean isColumnCreated(String regexColumnName, String fileName, Packages directory) {
    try {
      workbook = new XSSFWorkbook(new File(directory.getPackagePath() + fileName + ".xlsx"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheetAt(0);
    row = sheet.getRow(0);

    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
      if (row.getCell(columnIndex).getStringCellValue().matches(regexColumnName)) return true;
    }
    return false;
  }

  /**
   * This function adds new column in the first empty column of first row
   *
   * @param columnName name of new column
   * @param fileName   e.g. my_file
   * @param directory  e.g. Packages.STATISTICS_FOLDER
   */
  public void addColumn(String columnName, String fileName, Packages directory) {
    String path = directory.getPackagePath() + fileName + ".xlsx";
    try {
      workbook = new XSSFWorkbook(new FileInputStream(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheetAt(0);
    row = sheet.getRow(0);

    XSSFCell cell = row.createCell(row.getLastCellNum() == -1 ? 0 : row.getLastCellNum());
    cell.setCellStyle(getDefaultColumnNameStyle());
    cell.setCellValue(columnName);

    try {
      FileOutputStream out = new FileOutputStream(new File(directory.getPackagePath() + fileName + ".xlsx"));
      workbook.write(out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This function adds new record below given column in the first empty cell
   *
   * @param regexColumnName name of column, we want to add record
   * @param value           new value of cell
   * @param fileName        e.g. my_file
   * @param directory       e.g. Packages.STATISTICS_FOLDER
   */
  public void addValueInFirstEmptyCellOfColumn(String regexColumnName, String value, String fileName, Packages directory) {
    String path = directory.getPackagePath() + fileName + ".xlsx";
    int new_blank_row_index = 0;
    try {
      workbook = new XSSFWorkbook(new FileInputStream(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheetAt(0);
    int indexOfColumn = 0;
    row = sheet.getRow(0);

    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
      if (row.getCell(columnIndex).getStringCellValue().matches(regexColumnName)) {
        indexOfColumn = columnIndex;
        break;
      }
    }

    if (sheet.getLastRowNum() == 0) new_blank_row_index = 1;
    else {
      for (int rowIndex = 1; rowIndex <= 99999999; rowIndex++) {
        if (isCellEmpty(rowIndex, indexOfColumn, fileName, directory)) {
          new_blank_row_index = rowIndex;
          break;
        }
      }
    }

    if (sheet.getRow(new_blank_row_index) != null) row = sheet.getRow(new_blank_row_index);
    else row = sheet.createRow(new_blank_row_index);
    row.createCell(indexOfColumn).setCellValue(value);

    for (int i = 0; i < row.getLastCellNum(); i++) sheet.autoSizeColumn(i);

    try {
      FileOutputStream out = new FileOutputStream(new File(path));
      workbook.write(out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> getDataFromColumn(String regexColumnName, String fileName, Packages directory) {
    List<String> values = new ArrayList<>();
    String path = directory.getPackagePath() + fileName + ".xlsx";
    try {
      workbook = new XSSFWorkbook(new FileInputStream(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheetAt(0);
    int indexOfColumn = 0;
    row = sheet.getRow(0);

    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
      if (row.getCell(columnIndex).getStringCellValue().matches(regexColumnName)) {
        indexOfColumn = columnIndex;
        break;
      }
    }

    if (isCellEmpty(1, indexOfColumn, fileName, directory)) return null;
    for (int rowIndex = 1; rowIndex <= 99999999; rowIndex++) {
      if (!isCellEmpty(rowIndex, indexOfColumn, fileName, directory)) {
        values.add(sheet.getRow(rowIndex).getCell(indexOfColumn).getStringCellValue());
      } else break;
    }
    /* System.out.println(sheet.getRow(0).getCell(indexOfColumn).getStringCellValue()); */
    return values;
  }

  /**
   * @param rowIndex    number or row (starts from 0)
   * @param columnIndex number or column (starts from 0)
   * @param fileName    e.g. my_file
   * @param directory   e.g. Packages.STATISTICS_FOLDER
   * @return true if cell is empty, or false if cell has value
   */
  private boolean isCellEmpty(int rowIndex, int columnIndex, String fileName, Packages directory) {
    String path = directory.getPackagePath() + fileName + ".xlsx";
    try {
      workbook = new XSSFWorkbook(new FileInputStream(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheetAt(0);
    row = sheet.getRow(rowIndex);
    XSSFCell cell;
    try {
      cell = row.getCell(columnIndex);
    } catch (NullPointerException e) {
      row = sheet.createRow(rowIndex);
      cell = row.getCell(columnIndex);
    }
    return cell == null || cell.getCellType() == CellType.BLANK;
  }

  /**
   * @param rowIndex  number or row (starts from 0)
   * @param fileName  e.g. my_file
   * @param directory e.g. Packages.STATISTICS_FOLDER
   * @return true if row is created, or no if row does not exist
   */
  private boolean isRowCreated(int rowIndex, String fileName, Packages directory) {
    String path = directory.getPackagePath() + fileName + ".xlsx";
    try {
      workbook = new XSSFWorkbook(new FileInputStream(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return workbook.getSheetAt(0).getRow(rowIndex) != null;
  }
}