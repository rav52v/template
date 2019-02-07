package main.java.poms;

import main.java.utils.Gui;
import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SamplePage extends PageBase {

    @FindBy(css = "sample path")
    private List<WebElement> sampleList;

    @FindBy(css = "button[data-analytics-interaction-value='closeIcon']")
    private WebElement sampleElement;


    private By sampleBy = By.cssSelector("span.actor-name");
    private By anotherBy = By.xpath("../div/a/figure/div/div/div");


    public SamplePage() {
        xxx();
    }

    private void xxx() {
        browser.openPage(Gui.getInstance().getSearchLinkAddress());
        file.captureScreenshot("dupa", 0);

        browser.closeDriver(2000);
    }


}