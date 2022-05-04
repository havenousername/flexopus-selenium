import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLogin() {
        MainPage page = new MainPage(driver);
        page.simpleTest();
    }


    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
