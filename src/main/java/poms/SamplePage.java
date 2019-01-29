package main.java.poms;

import main.java.utils.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SamplePage extends PageBase {

    @FindBy(css = "sample path")
    private List<WebElement> sampleList;

    @FindBy(css = "button[data-analytics-interaction-value='closeIcon']")
    private WebElement sampleElement;

    private By sampleBy = By.cssSelector("span.actor-name");
    private By anotherBy = By.xpath("../div/a/figure/div/div/div");


    public SamplePage() {
        browser.openPage("https://allegro.pl/");
        click.click(sampleElement);
        sleeper(2000);
    }


}