package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ApplyCookies;
import utils.LocalStorageManipulations;

import java.time.Duration;

public class LoginPage extends PageBase  {
    private final By cookiesPopup = By.className("overlay__container");
    private final By cookiesSubmit = By.className("overlay__button");

    private final By emailInput = By.xpath("/html/body/section/div/div[3]/div/div/div/div[2]/form/div[1]/p/input");
    private final By passwordInput = By.xpath("/html/body/section/div/div[3]/div/div/div/div[2]/form/div[2]/p/input");
    private final By submitButton  = By.xpath("//button[@type='submit']");
    private final ApplyCookies createCookies;

    public LoginPage(WebDriver driver) {
        super(driver);
        goToLogin();
        createCookies = new ApplyCookies(driver);
    }

    public void goToLogin() {
        this.driver.get(BASE_URL);
    }

    public void initializeLocalStorage() {
        new LocalStorageManipulations(driver, wait, false)
                .initializeApp(Duration.ofMillis(100));
    }

    public void cookieBannerTest() {
        Assert.assertTrue(hasCookiesBanner());
        WebElement cookieAgreeButton = this.waitVisibilityAndFindElement(cookiesSubmit);
        cookieAgreeButton.click();


        wait.until(ExpectedConditions.invisibilityOfAllElements(cookieAgreeButton));
    }

    public boolean hasCookiesBanner() {
        return !driver.findElements(cookiesPopup).isEmpty();
    }

    public void loginProcessTest(String testEmail, String testPassword, Boolean save) {
        if (hasCookiesBanner()) {
            throw new RuntimeException("Please run cookiesBannerTestBefore");
        }

        WebElement email = waitVisibilityAndFindElement(emailInput);
        WebElement password = waitVisibilityAndFindElement(passwordInput);

        email.sendKeys(testEmail);
        password.sendKeys(testPassword);

        WebElement submit = waitVisibilityAndFindElement(submitButton);
        submit.click();

        // main page
        String redirectedUrl = BASE_URL + "/book/select-book-type";
        wait.until(ExpectedConditions.urlToBe(redirectedUrl));
        if (save) {
            createCookies.saveCookies();
        }
    }
}
