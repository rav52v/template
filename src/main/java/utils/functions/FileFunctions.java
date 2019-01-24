package main.java.utils.functions;

import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.*;
import java.nio.file.Files;

public class FileFunctions  extends BaseFunction {

    public void captureScreenshot(String fileName, int zoom) {
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("document.body.style.zoom='" + zoom + "%'");
        File scrFile = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);
        File target = new File(pathOutputFolder.toAbsolutePath().toString() + "/" + fileName + ".png");
        try {
            if (target.exists())
                target.delete();

            Files.copy(scrFile.toPath(), target.toPath());

            log.debug("File copied to {" + pathOutputFolder.toAbsolutePath().toString() + "\\" + fileName + ".png}");
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        js.executeScript("document.body.style.zoom='0'");
    }

    public void captureScreenshotOfElement(String fileName, WebElement element) {
        log.debug("Capture image of element {" + getElementInfo(element) + "}");

        File target = new File(pathOutputFolder.toAbsolutePath().toString() + "/" + fileName + ".png");
        if (target.exists())
            target.delete();

        File screen = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);

        Point p = element.getLocation();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        try {
            BufferedImage img = ImageIO.read(screen);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);
            ImageIO.write(dest, "png", screen);
            Files.copy(screen.toPath(), target.toPath());

            log.debug("File copied to {" + pathOutputFolder.toAbsolutePath().toString() + "\\" + fileName + ".png}");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RasterFormatException ex) {
            log.error(String.format("%s\nelement - parentX: %d, parentY: %d, width: %d, height: %d\nbrowser - x: %d, y: %d",
                    ex.toString(), p.getX(), p.getY(), width, height, driver.getDriver().manage().window().getSize().width,
                    driver.getDriver().manage().window().getSize().height));
        }
    }

    public void saveTextToFile(String textValue, String fileName, boolean append) {
        File target = new File(pathOutputFolder.toAbsolutePath().toString() + "/" + fileName + ".txt");

        try (FileWriter fw = new FileWriter(target, append);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(textValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
