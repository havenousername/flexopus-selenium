import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.TimeoutException;
import pages.LoginPage;
import pages.SettingsPage;

import java.time.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAuthenticationProcessTest extends BasicTest  {
    private final String testEmail = "seleniumtest@gmail.com";
    private final String testPassword = "SeleniumTesting01";

    private final String testEmailWrong = "selen@gmail.com";
    private final String testPasswordWrong = "SeleniumTesting0";

    @Test
    // test valid login first
    public void testLogin_1() {
        LoginPage page = new LoginPage(driver);
        page.initializeLocalStorage();
        page.goToLogin();
        page.testTitle();
        page.cookieBannerTest();
        page.loginProcessTest(testEmail, testPassword, true);
    }

    @Test(expected = TimeoutException.class)
    // test invalid login second
    public void testLogin_2() {
        LoginPage page = new LoginPage(driver);
        page.cookieBannerTest();
        page.loginProcessTest(testEmail, testPasswordWrong, false);
    }

    @Test
    // test settings page at last
    public void testLogin_3() {
        SettingsPage page = new SettingsPage(driver, testEmail, testPassword);
        page.changeAvatar();
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
