package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ApplyCookies;
import utils.BookableType;
import utils.Calendars;
import utils.LocalStorageManipulations;

import java.io.UnsupportedEncodingException;

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


    public BookingProcessTestPages(WebDriver driver) {
        super(driver);
        goToSelectBookDate();
        new LocalStorageManipulations(driver, wait, true).initializeApp();
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
