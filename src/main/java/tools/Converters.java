package main.java.tools;

public class Converters {

  private String convertDateToSqlFormat(String date) {
    return String.format("%04d-%02d-%02d", getYear(date), getMonth(date), getDay(date));
  }

  private int getYear(String date) {
    if (date.length() < 4)
      return 1;

    String result = date.substring(date.length() - 4);

    if (result.matches("[0-9]{4}"))
      return Integer.parseInt(result);
    else
      return 1;
  }

  private int getMonth(String date) {
    String result = date.replaceAll("[\\d]|[\\s]", "");

    if (result.length() < 3)
      return 1;

    if (result.contains("stycz")) {
      result = "01";
    } else if (result.contains("lut")) {
      result = "02";
    } else if (result.contains("mar")) {
      result = "03";
    } else if (result.contains("kwie")) {
      result = "04";
    } else if (result.contains("maj")) {
      result = "05";
    } else if (result.contains("czerw")) {
      result = "06";
    } else if (result.contains("lip")) {
      result = "07";
    } else if (result.contains("sierp")) {
      result = "08";
    } else if (result.contains("wrze")) {
      result = "09";
    } else if (result.contains("październik")) {
      result = "10";
    } else if (result.contains("listopad")) {
      result = "11";
    } else if (result.contains("grud")) {
      result = "12";
    }

    return Integer.parseInt(result);
  }

  private int getDay(String date) {
    if (!Character.isDigit(date.charAt(0)))
      return 1;
    return Integer.parseInt(date.replaceAll(" .+ \\d{4}$", ""));
  }
}
