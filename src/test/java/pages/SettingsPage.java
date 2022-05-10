package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.time.Duration;

public class SettingsPage extends PageBase {
    private final String profileInformationXPath = "//*[@id=\"user_form\"]//div[2]";
    private final By nameInput = By.xpath( profileInformationXPath + "//*[contains(@class, \"grid__element-left\")]//input[1]");
    private final By departmentInput = By.xpath(profileInformationXPath + "//*[contains(@class, \"grid__element-left\")]//input[2]");
    private final By functionInput = By.xpath(profileInformationXPath + "//*[contains(@class, \"grid__element-right\")]//input[2]");
    private final By emailInput = By.xpath(profileInformationXPath + "//*[contains(@class, \"grid__element-right\")]//input[1]");
    private final By aboutMeInput = By.xpath("//*[@id=\"user_form\"]/div[2]/div[2]/textarea");
    private final By submitButton  = By.xpath("//button[@type='submit']");
    private final By logoutButton = By.xpath("//*[contains(@class, \"is-logout\")]");
    private final By notificationTitle = By.xpath("//*[contains(@class, \"notification__title\")]");
    private final By notificationText = By.xpath("//*[contains(@class, \"notification__desc\")]");
    private final String initialName;


    public SettingsPage(WebDriver driver, String email, String password) {
        this(driver, email, password, "");
    }

    public SettingsPage(WebDriver driver, String email, String password, String initialName) {
        super(driver);
        LoginPage page = new LoginPage(driver);
        page.cookieBannerTest();
        page.loginProcessTest(email, password, false);
        wait.withTimeout(Duration.ofSeconds(1));
        this.driver.get(BASE_URL + "/settings");
        this.initialName = initialName;
    }

    public void resetProfileInfo() {
        changeProfileInfo(
                initialName,
                "/",
                "/",
                "/"
        );
    }


    public void changeProfileInfo(String nameStr, String aboutMeStr, String departmentStr, String functionStr) {
        WebElement name = waitVisibilityAndFindElement(nameInput);
        WebElement email = waitVisibilityAndFindElement(emailInput);
        WebElement aboutMe = waitVisibilityAndFindElement(aboutMeInput);
        WebElement department = waitVisibilityAndFindElement(departmentInput);
        WebElement function = waitVisibilityAndFindElement(functionInput);

        if (email.isEnabled()) {
            throw new RuntimeException("Email field should be disabled");
        }

        if (nameStr == null) {
            throw new RuntimeException("New \"name\" input name should be provided");
        }
        name.clear();
        name.sendKeys(nameStr);

        aboutMe.clear();
        aboutMe.sendKeys(aboutMeStr);

        department.clear();
        department.sendKeys(departmentStr);

        function.clear();
        function.sendKeys(functionStr);

        WebElement submit = waitVisibilityAndFindElement(submitButton);
        submit.click();

        wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(notificationTitle));
        wait.until(ExpectedConditions.textToBe(notificationTitle, "Erfolg"));
        wait.until(ExpectedConditions.textToBe(notificationText, "Profil erfolgreich gespeichert!"));
    }

    public void changeAvatar() {
        By changeAvatarSelector = By.className("user-card__pen__icon");
        WebElement changeAvatar = waitVisibilityAndFindElement(changeAvatarSelector);
        changeAvatar.click();

        WebElement inputFile = this.driver.findElement(
                By.xpath("//*[@id=\"confirm-dialog\"]/div[2]/div/input")
        );

        File f = new File("src/test/assets/1.png");
        inputFile.sendKeys(f.getAbsolutePath());

        WebElement submit = waitVisibilityAndFindElement(
                By.xpath("//*[@id=\"confirm-dialog\"]/div[3]/div/button[1]")
        );

        submit.click();
    }

    public void testLogout() {
        WebElement logout = waitVisibilityAndFindElement(logoutButton);
        logout.click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/login"));
    }
}
