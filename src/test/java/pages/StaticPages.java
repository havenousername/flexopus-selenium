package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class StaticPages extends LoginPage {
    private final List<String> staticPagesUrl = new ArrayList<>();
    // one - one with static page url
    private final List<By> staticPagesElements = new ArrayList<>();
    private final List<String> staticElementsContent = new ArrayList<>();

    public StaticPages(WebDriver driver) {
        super(driver);
        fillPages();
    }

    public void fillPages() {
        staticPagesUrl.add( BASE_URL +  "/impressum");
        staticPagesUrl.add( BASE_URL + "/data-policy" );

        staticPagesElements.add(By.className("header"));
        staticPagesElements.add(By.className("header"));

        staticElementsContent.add("Impressum");
        staticElementsContent.add("Datenschutzinformationen für die Nutzung des Buchungssystems");
    }

    public int size() {
        return this.staticPagesUrl.size();
    }

    public void testPageByIndex(int index) {
        driver.get(staticPagesUrl.get(index));

        wait.until(ExpectedConditions.urlToBe(staticPagesUrl.get(index)));

        Assert.assertEquals(staticElementsContent.get(index), waitVisibilityAndFindElement(staticPagesElements.get(index)).getText());
    }
}
