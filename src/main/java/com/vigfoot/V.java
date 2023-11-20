package com.vigfoot;

import com.vigfoot.log.factory.LogRecord;

public class V {

    public enum Level {
        ZERO("L0")
        , ONE("L1")
        , TWO("L2")
        , THREE("L3")
        , FOUR("L4")
        , FIVE("L5")
        , SIX("L6")
        , SEVEN("L7")
        , EIGHT("L8")
        , NINE("L9");

        final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

        public String prefix() {
            return prefix;
        }
    }

    private static int defaultLevel;

    private V() {
    }

    protected void setDefaultLevel(int defaultLevel) {
        V.defaultLevel = defaultLevel;
    }

    private String getCallerClassName() {
        for (int i = 0; i < new Throwable().getStackTrace().length; i++) {
            final String className = new Throwable().getStackTrace()[i].getClassName();
            if (className.contains("com.vigfoot.log.factory")) continue;

            return className;
        }
        return null;
    }

    private static void log(Level level, String log, Object... arguments) {
        if (!isUpperLogLevel(level.ordinal())) return;
        LogRecord.writeLog(level, log, arguments);
    }

    public static void log0(String log, Object... arguments) {
        log(Level.ZERO, log, arguments);
    }

    public static void log1(String log, Object... arguments) {
        log(Level.ONE, log, arguments);
    }

    public static void log2(String log, Object... arguments) {
        log(Level.TWO, log, arguments);
    }

    public static void log3(String log, Object... arguments) {
        log(Level.THREE, log, arguments);
    }

    public static void log4(String log, Object... arguments) {
        log(Level.FOUR, log, arguments);
    }

    public static void log5(String log, Object... arguments) {
        log(Level.FIVE, log, arguments);
    }

    public static void log6(String log, Object... arguments) {
        log(Level.SIX, log, arguments);
    }

    public static void log7(String log, Object... arguments) {
        log(Level.SEVEN, log, arguments);
    }

    public static void log8(String log, Object... arguments) {
        log(Level.EIGHT, log, arguments);
    }

    public static void log9(String log, Object... arguments) {
        log(Level.NINE, log, arguments);
    }

    private static boolean isUpperLogLevel(int logLevel) {
        return logLevel >= defaultLevel;
    }
}