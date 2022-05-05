package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoBackPageBase extends PageBase {
    protected final By goBackSelector = By.className("go-back-wrapper");
    protected GoBackPageBase(WebDriver driver) {
        super(driver);
    }

    public void goBack() {
        if (driver.findElements(goBackSelector).isEmpty()) {
            System.out.println("No back button presented");
            driver.navigate().back();
            return;
        }

        WebElement goBack = waitVisibilityAndFindElement(goBackSelector);
        goBack.click();
    }
}
