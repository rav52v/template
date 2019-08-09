package main.java.enums;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public enum Packages {
  OUTPUT_PACKAGE("_output"),
  INPUT_PACKAGE("_input"),
  DOWNLOAD_PACKAGE("_downloads"),
  THUMBS("_thumbs"),
  STATISTICS_PACKAGE("_stats");

  private String folder;

  Packages(String values) {
    folder = values;
  }

  /**
   * Creates directory if does not exist
   *
   * @return relative path to directory
   */
  public String getPackagePath() {
    if (new File(Paths.get(folder).toAbsolutePath().toString()).mkdirs())
      LogManager.getLogger().debug("Created directory {" + Paths.get(folder).toAbsolutePath() + "}.");
    return Paths.get(folder).toAbsolutePath() + File.separator;
  }

  /**
   * @return amount of files inside directory
   */
  public int getElementsInsideAmount() {
    try {
      return new File(new File("").getCanonicalFile().toPath().toAbsolutePath().toString()
              + File.separator + folder).listFiles().length;
    } catch (IOException | NullPointerException e) {
      return 0;
    }
  }

  /**
   * @param fileName e.g. sample_file.txt
   * @return true if files exists in directory, or false if not
   */
  public boolean isFileInDirectory(String fileName) {
    File[] files;
    try {
      files = new File(new File("").getCanonicalFile().toPath().toAbsolutePath().toString()
              + File.separator + folder).listFiles();
    } catch (IOException | NullPointerException e) {
      return false;
    }
    for (File file : files) if (file.getName().equals(fileName)) return true;
    return false;
  }

  /**
   * @param fileName e.g. sample_file.txt
   */
  public void deleteFileFromDirectory(String fileName) {
    try {
      new File(new File("").getCanonicalFile().toPath().toAbsolutePath().toString()
              + File.separator + folder + File.separator + fileName).delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}