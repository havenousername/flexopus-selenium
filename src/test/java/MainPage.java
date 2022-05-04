import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends PageBase  {
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get(WEBPAGE_NAME);
    }

    public void simpleTest() {
        boolean hasCookiesBanner = !driver.findElements(By.className("screen-card")).isEmpty();
        Assert.assertTrue(hasCookiesBanner);
    }
}
