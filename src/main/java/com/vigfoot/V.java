package com.vigfoot;

import com.vigfoot.log.factory.LogRecord;

public class V {

    private V() {
    }

    private static void log(LogRecord.Level level, String log, Object... arguments) {
        LogRecord.writeLog(level, log, arguments);
    }

    public static void log0(String log, Object... arguments) {
        log(LogRecord.Level.ZERO, log, arguments);
    }

    public static void log1(String log, Object... arguments) {
        log(LogRecord.Level.ONE, log, arguments);
    }

    public static void log2(String log, Object... arguments) {
        log(LogRecord.Level.TWO, log, arguments);
    }

    public static void log3(String log, Object... arguments) {
        log(LogRecord.Level.THREE, log, arguments);
    }

    public static void log4(String log, Object... arguments) {
        log(LogRecord.Level.FOUR, log, arguments);
    }

    public static void log5(String log, Object... arguments) {
        log(LogRecord.Level.FIVE, log, arguments);
    }

    public static void log6(String log, Object... arguments) {
        log(LogRecord.Level.SIX, log, arguments);
    }

    public static void log7(String log, Object... arguments) {
        log(LogRecord.Level.SEVEN, log, arguments);
    }

    public static void log8(String log, Object... arguments) {
        log(LogRecord.Level.EIGHT, log, arguments);
    }

    public static void log9(String log, Object... arguments) {
        log(LogRecord.Level.NINE, log, arguments);
    }
}