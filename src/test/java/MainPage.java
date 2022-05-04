import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    protected static String WEBPAGE_NAME = "https://demo.flexopus.apicore.de";
    protected WebDriver driver;
    protected WebDriverWait wait;


    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver,10);
    }

    private WebElement waitVisibiiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }


    @Test
    public void simpleTest() {
        this.driver.get(WEBPAGE_NAME);

        boolean hasCookiesBanner = !driver.findElements(By.className("screen-card")).isEmpty();
        Assert.assertTrue(hasCookiesBanner);
    }

    @After
    public void close() {
        if (driver != null) {
            driver.close();
        }
    }
}
