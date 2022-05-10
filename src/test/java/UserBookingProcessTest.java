import org.junit.Test;
import pages.BookingProcessTestPages;
import utils.BookableType;
import utils.Calendars;

import java.util.Date;
import java.util.Random;

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
        testBookableTypeProcessesRand(BookableType.WORKING_SPACE);
        testBookableTypeProcessesRand(BookableType.PARKING_SPOT);
        // deprecated in latest release
        // testBookableTypeProcessesRand(BookableType.MEETING_ROOM);
    }

    private void testBookableTypeProcesses(BookableType bookableType) {
        bookingProcessTestPages.goToSelectBookDate(bookableType);
        bookingProcessTestPages.selectBookDate(11);
        bookingProcessTestPages.selectBookTimeFrom(3);
        bookingProcessTestPages.selectBookTimeTo(6);
        bookingProcessTestPages.searchBookablesInTimeRange(bookableType);
        bookingProcessTestPages.selectLocation(1);
        bookingProcessTestPages.hoverAndSelectBookable(1, bookableType);
        bookingProcessTestPages.goToSelectBookDate();
    }

    private void testBookableTypeProcessesRand(BookableType bookableType) {
        bookingProcessTestPages.goToSelectBookDate(bookableType);
        Random rand = new Random();
        int day = rand.nextInt(Calendars.lastMonthDay());
        bookingProcessTestPages.selectBookDate(day);
        bookingProcessTestPages.selectBookTimeFrom(3);
        bookingProcessTestPages.selectBookTimeTo(6);
        bookingProcessTestPages.searchBookablesInTimeRange(bookableType);
        bookingProcessTestPages.selectLocation(1);
        bookingProcessTestPages.hoverAndSelectBookable(1, bookableType);
        bookingProcessTestPages.goToSelectBookDate();
    }
}
