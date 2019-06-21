package main.java.tools;

import main.java.enums.Packages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.VideoFormatKeys.*;


public class ScreenRecorderService {

  private static ScreenRecorderService instance;

  private ScreenRecorder screenRecorder;
  private Logger log = LogManager.getLogger();
  private final String PATH_TO_OUTPUT_FOLDER = Packages.OUTPUT_FOLDER.getPackagePath();

  private ScreenRecorderService(){
  }

  public static ScreenRecorderService getScreenRecorder() {
    if (instance == null) instance = new ScreenRecorderService();
    return instance;
  }

  public void startRecordingScreen() {
    GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    try {
      screenRecorder = new ScreenRecorder(
              gc,
              null,
              new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
              new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG,
                      CompressorNameKey, ENCODING_QUICKTIME_JPEG, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                      QualityKey, 0.5f, KeyFrameIntervalKey, 15 * 60),
              new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
                      Rational.valueOf(30)),
              null,
              new File(PATH_TO_OUTPUT_FOLDER));
      screenRecorder.start();
      log.debug("Recording started.");
    } catch (IOException | AWTException e) {
      e.printStackTrace();
    }
  }

  public void stopRecordingScreen() {
    try {
      screenRecorder.stop();
      log.debug("Recording stopped, new file created in directory: {" + PATH_TO_OUTPUT_FOLDER + "}.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
