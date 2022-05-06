import org.junit.Test;
import pages.BookingProcessTestPages;
import utils.BookableType;

import java.util.concurrent.TimeUnit;

public class UserBookingProcessTest extends BasicTest {
    private BookingProcessTestPages bookingProcessTestPages;

    @Override
    public void setup() {
        super.setup();
        bookingProcessTestPages = new BookingProcessTestPages(driver);
        bookingProcessTestPages.goToSelectBookDate();
    }

    @Test
    public void testBookableTypeProcesses() {
       testBookableTypeProcesses(BookableType.WORKING_SPACE);
       testBookableTypeProcesses(BookableType.PARKING_SPOT);
       testBookableTypeProcesses(BookableType.MEETING_ROOM);
    }

    private void testBookableTypeProcesses(BookableType bookableType) {
        bookingProcessTestPages.goToSelectBookDate(bookableType);
        bookingProcessTestPages.selectBookDate(11);
        bookingProcessTestPages.selectBookTimeFrom(3);
        bookingProcessTestPages.selectBookTimeTo(6);
        bookingProcessTestPages.searchBookablesInTimeRange(bookableType);
        bookingProcessTestPages.goBack();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        bookingProcessTestPages.goBack();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
    }
}
