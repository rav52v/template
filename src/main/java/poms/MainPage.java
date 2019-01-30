package main.java.poms;

import main.java.utils.Gui;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends PageBase {
    @FindBy(css = "img")
    private List<WebElement> sample1;

    @FindBy(css = "#ctl00_lnkCopyright")
    private WebElement sample2;

    @FindBy(css = "input[name=reg_email__]")
    private WebElement sample3;

    @FindBy(id = "sample")
    private WebElement sample4;

    private By cloud = By.cssSelector("._5v-0._53im");

    public MainPage() {
        firstMethod();
    }


    private void firstMethod() {
        browser.openPage(Gui.getInstance().getSearchLinkAddress());
        sleeper(1000);

        browser.openNewTab();
        browser.openPage("https://www.youtube.com/?hl=pl&gl=PL");
        file.captureScreenshot("not main", 0);
        browser.switchToMainTab();
        browser.openPage("https://translate.google.com/?hl=pl");
        file.captureScreenshot("main", 0);
        browser.switchToSecondTab();
        browser.openPage("https://translate.google.com/?hl=pl");
        browser.closeTab();

        browser.closeDriver(2000);
    }


}