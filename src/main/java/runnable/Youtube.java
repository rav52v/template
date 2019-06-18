package main.java.runnable;


import java.io.File;
import java.net.URL;
import com.github.axet.vget.VGet;
import main.java.enums.Packages;

public class Youtube {

  public static void main(String[] args) {
    try {
      String url = "https://www.youtube.com/watch?v=jNQXAC9IVRw";
      String path = Packages.OUTPUT_FOLDER.getPackagePath();
      VGet v = new VGet(new URL(url), new File(path));
      v.download();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}