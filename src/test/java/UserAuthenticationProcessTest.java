import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import pages.LoginPage;
import pages.SettingsPage;

import java.time.Duration;

public class UserAuthenticationProcessTest extends BasicTest  {
    private final String testEmail = "seleniumtest@gmail.com";
    private final String testPassword = "SeleniumTesting01";

    private final String testEmailWrong = "selen@gmail.com";
    private final String testPasswordWrong = "SeleniumTesting0";

    @Test
    public void testLogin() {
        LoginPage page = new LoginPage(driver);
        page.initializeLocalStorage();
        page.goToLogin();
        page.testTitle();
        page.cookieBannerTest();
        page.loginProcessTest(testEmail, testPassword, true);
    }

    @Test(expected = TimeoutException.class)
    public void testWrongEmailLogin() {
        LoginPage page = new LoginPage(driver);
        page.cookieBannerTest();
        page.loginProcessTest(testEmail, testPasswordWrong, false);
    }

    @Test
    public void testSettingsForm() {
        SettingsPage page = new SettingsPage(driver, testEmail, testPassword);
        page.changeProfileInfo(
                "Test Name",
                "About me",
                "department",
                "Function New"
        );
        page.resetProfileInfo();
        page.wait(Duration.ofSeconds(1));
        page.testLogout();
    }
}
