package com.example.gogot.model.settings;

public class Timers {
    private static long[] timers = {180000, 180000, 180000};
    private static Boolean isEqual = true;
    private static Boolean timersOn = false;

    public static long[] getTimers() {
        return timers;
    }

    public static void setTimersOn(Boolean timersOn) {
        Timers.timersOn = timersOn;
    }

    public static void setIsEqual(Boolean isEqual) {
        Timers.isEqual = isEqual;
    }

    public static Boolean getIsEqual() {
        return isEqual;
    }

    public static Boolean getTimersOn() {
        return timersOn;
    }
}
