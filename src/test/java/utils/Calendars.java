package utils;

import java.util.Calendar;

public class Calendars {
    public static int lastMonthDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DATE);
    }
}
