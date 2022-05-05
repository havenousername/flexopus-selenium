package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class PageBase {
    protected static String BASE_URL = "https://demo.flexopus.apicore.de";
    protected String timezone = "Europe/Berlin";
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void wait(Duration duration) {
        this.wait.withTimeout(duration);
    }

    protected WebElement waitVisibilityAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public void testTitle() {
        final String title = "Flexopus - The Desk Sharing Solution";
        Assert.assertEquals(title, this.driver.findElement(By.tagName("title")).getAttribute("innerHTML"));
    }
}
