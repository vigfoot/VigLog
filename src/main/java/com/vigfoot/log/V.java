package com.vigfoot.log;

import com.vigfoot.log.factory.LogRecord;

@VigLog
public class V {

    private V() {
    }

    private static void log(LogRecord.LEVEL level, String log, Object... arguments) {
        LogRecord.writeLog(level, log, arguments);
    }

    public static void log0(String log, Object... arguments) {
        log(LogRecord.LEVEL.ZERO, log, arguments);
    }

    public static void log1(String log, Object... arguments) {
        log(LogRecord.LEVEL.ONE, log, arguments);
    }

    public static void log2(String log, Object... arguments) {
        log(LogRecord.LEVEL.TWO, log, arguments);
    }

    public static void log3(String log, Object... arguments) {
        log(LogRecord.LEVEL.THREE, log, arguments);
    }

    public static void log4(String log, Object... arguments) {
        log(LogRecord.LEVEL.FOUR, log, arguments);
    }

    public static void log5(String log, Object... arguments) {
        log(LogRecord.LEVEL.FIVE, log, arguments);
    }

    public static void log6(String log, Object... arguments) {
        log(LogRecord.LEVEL.SIX, log, arguments);
    }

    public static void log7(String log, Object... arguments) {
        log(LogRecord.LEVEL.SEVEN, log, arguments);
    }

    public static void log8(String log, Object... arguments) {
        log(LogRecord.LEVEL.EIGHT, log, arguments);
    }

    public static void log9(String log, Object... arguments) {
        log(LogRecord.LEVEL.NINE, log, arguments);
    }
}