package main.java.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static main.java.utils.ConfigService.getConfigService;

public class Database {
  private static Database instance;

  private Connection con;
  private Logger log;

  private Database() {
    log = LogManager.getLogger(this);
    try {
      con = DriverManager.getConnection(
              getConfigService().getStringProperty("sql.conUrl"),
              getConfigService().getStringProperty("sql.login"),
              getConfigService().getStringProperty("sql.password"));
      con.setAutoCommit(true);
      log.info("Connected to database {" + getConfigService().getStringProperty("sql.conUrl") + "}.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Database getInstance() {
    if (instance == null) instance = new Database();
    return instance;
  }

  /**
   * @param query full query to execute
   */
  public void executeQuery(String query) {
    try {
      PreparedStatement pstmt = con.prepareStatement(query);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * @param table  name of table
   * @param column name of column
   * @param value  value to check (implemented for Integer or String)
   * @return if record occurs
   */
  public boolean checkIfRecordExist(String table, String column, Object value) {
    String query = "SELECT " + column + " FROM " + table + " WHERE " + column + " = ?";
    ResultSet rs;
    try {
      PreparedStatement pstmt = con.prepareStatement(query);

      if (value instanceof java.lang.String) pstmt.setString(1, (String) value);
      else if (value instanceof java.lang.Integer) pstmt.setInt(1, (Integer) value);
      else if (value instanceof java.lang.Long) pstmt.setLong(1, (Long) value);

      rs = pstmt.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return false;
  }

  /**
   * @param table          name of table
   * @param columnWhere    column type 'WHERE'
   * @param valueWhere     record from columnWhere (implemented for Integer or String)
   * @param columnToUpdate column containing record to update
   * @param newValue       new value (implemented for Integer or String)
   */
  public void updateRecord(String table, String columnWhere, Object valueWhere, String columnToUpdate, Object newValue) {
    String query = "UPDATE " + table + " SET " + columnToUpdate + " = ? WHERE " + columnWhere + " = ?";
    try {
      PreparedStatement pstmt = con.prepareStatement(query);

      if (newValue instanceof java.lang.Long) pstmt.setLong(1, (Long) newValue);
      else if (newValue instanceof java.lang.Integer) pstmt.setInt(1, (Integer) newValue);
      else pstmt.setString(1, (String) newValue);

      if (valueWhere instanceof java.lang.Long) pstmt.setLong(2, (Long) valueWhere);
      else if (valueWhere instanceof java.lang.Integer) pstmt.setInt(2, (Integer) valueWhere);
      else pstmt.setString(2, (String) valueWhere);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * @param table   name of table
   * @param columns columns names
   * @param values  values to insert
   */
  public void insertRecords(String table, String[] columns, Object[] values) {
    if (columns.length != values.length) {
      log.error("Columns length {" + columns.length + "} is not the same as values length {"
              + values.length + "}.");
      throw new RuntimeException("Columns length {" + columns.length + "} is not the same as number of values {"
              + values.length + "}.");
    }

    String query = String.format("INSERT into %s (%s) VALUES (%s?)",
            table, String.join(", ", columns), "?, ".repeat(columns.length - 1));

    PreparedStatement pstmt;
    try {
      pstmt = con.prepareStatement(query);
      for (int i = 0; i < values.length; i++) {
        if (values[i] instanceof java.lang.Long) pstmt.setLong(i + 1, (Long) values[i]);
        else if (values[i] instanceof java.lang.Integer) pstmt.setInt(i + 1, (Integer) values[i]);
        else pstmt.setString(i + 1, (String) values[i]);
      }
      pstmt.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * @param table            name of table
   * @param columnWhere      column type 'WHERE'
   * @param valueWhere       record from columnWhere
   * @param columnWithResult column with result
   * @return String value given from result set
   */
  public String getString(String table, String columnWhere, Object valueWhere, String columnWithResult) {
    String query = "SELECT * FROM " + table + " WHERE " + columnWhere + " = ?";
    ResultSet rs;
    try {
      PreparedStatement pstmt = con.prepareStatement(query);

      if (valueWhere instanceof java.lang.Long) pstmt.setLong(1, (Long) valueWhere);
      else if (valueWhere instanceof java.lang.Integer) pstmt.setInt(1, (Integer) valueWhere);
      else pstmt.setString(1, (String) valueWhere);

      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getString(columnWithResult);
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return "";
  }

  /**
   * @param table            name of table
   * @param columnWhere      column type 'WHERE'
   * @param valueWhere       record from columnWhere
   * @param columnWithResult column with result
   * @return Integer value given from result set
   */
  public Integer getInt(String table, String columnWhere, Object valueWhere, String columnWithResult) {
    String query = "SELECT * FROM " + table + " WHERE " + columnWhere + " = ?";
    ResultSet rs;
    try {
      PreparedStatement pstmt = con.prepareStatement(query);

      if (valueWhere instanceof java.lang.Long) pstmt.setLong(1, (Long) valueWhere);
      else if (valueWhere instanceof java.lang.Integer) pstmt.setInt(1, (Integer) valueWhere);
      else pstmt.setString(1, (String) valueWhere);

      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getInt(columnWithResult);
    } catch (SQLException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return 0;
  }
}