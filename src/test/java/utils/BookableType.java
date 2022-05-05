package utils;

public enum BookableType {
    UNKNOWN(""),
    WORKING_SPACE("DESK"),
    PARKING_SPOT("PARKING_SPACE"),
    MEETING_ROOM("MEETING_ROOM");

    private final String name;

    BookableType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
