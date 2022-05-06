import org.junit.Test;
import pages.BookingProcessTestPages;
import utils.BookableType;

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
        bookingProcessTestPages.selectLocation(1);
        bookingProcessTestPages.hoverAndSelectBookable(1, bookableType);
        bookingProcessTestPages.goToSelectBookDate();
    }
}
