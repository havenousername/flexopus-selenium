package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ApplyCookies;
import utils.BookableType;
import utils.Calendars;
import utils.LocalStorageManipulations;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class BookingProcessTestPages extends GoBackPageBase {
    private final By workStationSelect = By.xpath(
            "//*[contains(@class, \"screen-card\")]//*[contains(@class, \"selector-container\")]//div[1]/div[1]/div[2]/button"
    );
    private final By meetingRoomsSelect = By.xpath(
            "//*[contains(@class, \"screen-card\")]//*[contains(@class, \"selector-container\")]//div[2]/div[1]/div[2]/button"
    );
    private final By parkingSelect = By.xpath(
            "//*[contains(@class, \"screen-card\")]//*[contains(@class, \"selector-container\")]//div[3]/div[1]/div[2]/button"
    );

    private final By selectFromSelector = By.xpath(
      "//*[contains(@class, \"screen-card__selector\")]//div[1]/div[1]/*[contains(@class, \"multiselect__tags\")]"
    );

    private final By selectToSelector = By.xpath(
            "//*[contains(@class, \"screen-card__selector\")]//div[2]/div[1]/*[contains(@class, \"multiselect__tags\")]"
    );

    private final By submitSelector = By.xpath("//*[@id=\"app\"]/div/div[3]/div/div/div/div[3]/div[1]/div[2]/button");

    private final By submitBooking = By.xpath("//*[@id=\"confirm-dialog\"]/div[3]/div/div/button");


    public BookingProcessTestPages(WebDriver driver) {
        super(driver);
        goToSelectBookDate();
        new LocalStorageManipulations(driver, wait, true).initializeApp(Duration.ofMillis(100));
        ApplyCookies applyCookies = new ApplyCookies(driver);
        applyCookies.apply();
    }

    public void goToSelectBookDate() {
        this.driver.get(BASE_URL + "/book/select-book-type");
    }

    public void goToSelectBookDate(BookableType bookableType) {
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/book/select-book-type"));
        WebElement processButton;
        switch (bookableType) {
            case MEETING_ROOM -> processButton = waitVisibilityAndFindElement(meetingRoomsSelect);
            case WORKING_SPACE -> processButton = waitVisibilityAndFindElement(workStationSelect);
            case PARKING_SPOT -> processButton = waitVisibilityAndFindElement(parkingSelect);
            default -> throw new RuntimeException("Such bookable type should not be selectable in the application");
        }

        processButton.click();
        wait.until(ExpectedConditions.urlToBe(createSelectBookByDateURL(bookableType)));
    }

    public void selectBookDate(Integer day) {
        if (day < 0 || day > Calendars.lastMonthDay()) {
            throw new IllegalArgumentException("Please provide for testing booking day between 0 and " + Calendars.lastMonthDay() + "\n");
        }

        String dayClass = "day-" + day;
        final By daySelector = By.xpath(
                "//div[contains(@class, 'vc-day') and contains(@class, '" + dayClass +"')]"
        );
        WebElement selectDay = waitVisibilityAndFindElement(daySelector);
        selectDay.click();
    }

    public void selectBookTimeFrom(int select) {
        WebElement selectFrom = waitVisibilityAndFindElement(selectFromSelector);
        selectFrom.click();
        final By selectOption = By.xpath(
            "//*[contains(@class, \"screen-card__selector\")]//div[1]/div[1]/*[contains(@class, \"multiselect__content\")]//li[" + select  + "]"
        );
        WebElement selectElement = waitVisibilityAndFindElement(selectOption);
        selectElement.click();
    }

    public void selectBookTimeTo(int select) {
        WebElement selectTo = waitVisibilityAndFindElement(selectToSelector);
        selectTo.click();
        final By selectOption = By.xpath(
                "//*[contains(@class, \"screen-card__selector\")]//div[2]/div[1]/*[contains(@class, \"multiselect__content\")]//li[" + select  + "]"
        );
        WebElement selectElement = waitVisibilityAndFindElement(selectOption);
        selectElement.click();
    }

    public void searchBookablesInTimeRange(BookableType bookableType) {
        WebElement submit = waitVisibilityAndFindElement(submitSelector);
        submit.click();
        try {
            wait.until(ExpectedConditions.urlContains(createSelectLocationURL(bookableType)));
        } catch (WebDriverException e) {
            System.out.println("Conflicting bookings");
        }
    }

    public void selectLocation(int select) throws NoSuchElementException {
        final By locationDiv = By.xpath("//*[contains(@class, \"location-select__container\")]//div[3]/div[1]/div[" + select + "]");

        WebElement location = waitVisibilityAndFindElement(locationDiv);
        location.click();
        wait.until(ExpectedConditions.urlContains("book/select-bookable"));
    }

    public void hoverAndSelectBookable(int select, BookableType bookableType) throws NoSuchElementException {
        var objectDriver = driver.switchTo().frame(waitVisibilityAndFindElement(By.id("floorplan")));
        WebElement selectedBookable = waitVisibilityAndFindElement(objectDriver, By.xpath("//*[@id='" + bookableType.getName() +"']//svg:g[" + select +"]"));
        Actions actions = new Actions(objectDriver);
        actions.moveToElement(selectedBookable).clickAndHold().build().perform();
        String fill = selectedBookable.findElement(By.tagName("path")).getCssValue("fill");
        String rgbColor = "rgb(255, 255, 255)";
        Assert.assertNotEquals("Values are not different. Not Excepted" + rgbColor + ", got " + fill + " at element " + selectedBookable.getAttribute("id"),rgbColor, fill);

        selectedBookable.click();
        driver.switchTo().parentFrame();
        WebElement submit = waitVisibilityAndFindElement(submitBooking);
        submit.click();
    }


    private String createSelectBookByDateURL(BookableType bookableType) {
        try {
            return BASE_URL + "/book/select-book-date/" + bookableType.getName() + "/" + this.encodeValue(timezone);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Wrong timezone provided. URL could not be created");
        }
    }

    private String createSelectLocationURL(BookableType bookableType) {
        try {
            return BASE_URL + "/book/select-location/" + bookableType.getName() + "/" + this.encodeValue(timezone);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Wrong timezone provided. URL could not be created");
        }
    }
}
