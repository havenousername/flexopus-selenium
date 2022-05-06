import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BasicTest {
    public WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        //var chromeProfilePath = "/Users/andreicristea/Library/Application Support/Google/Chrome/Default";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("disable-extensions", "disable-popup-blocking");


        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }


    @After
    public void close() {
//        if (driver != null) {
//            driver.quit();
//        }
    }
}
