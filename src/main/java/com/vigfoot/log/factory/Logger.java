package com.vigfoot.log.factory;

public class Logger implements LogInterface {

    enum Level {
        ZERO("[Log 0] "), ONE("[Log 1] "), TWO("[Log 2] "), THREE("[Log 3] "), FOUR("[Log 4] "), FIVE("[Log 5] "), SIX("[Log 6] "), SEVEN("[Log 7] "), EIGHT("[Log 8] "), NINE("[Log 9] ");

        final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

    }

    private final LogRecord logRecord = new LogRecord();//logmanager로 대체 예정(thread관리)

    private void log(Level level, String log, Object... arguments) {
        final String callerClassName = getCallerClassName();

        logRecord.log(level.prefix + log, arguments);
    }

    private String getCallerClassName() {
        for (int i = 0; i < new Throwable().getStackTrace().length; i++) {
            final String className = new Throwable().getStackTrace()[i].getClassName();
            if (className.contains("com.vigfoot.log.factory")) continue;

            return className;
        }
        return null;
    }

    public void log0(String log, Object... arguments) {
        log(Level.ZERO, log, arguments);
    }

    public void log1(String log, Object... arguments) {
        log(Level.ONE, log, arguments);
    }

    public void log2(String log, Object... arguments) {
        log(Level.TWO, log, arguments);
    }

    public void log3(String log, Object... arguments) {
        log(Level.THREE, log, arguments);
    }

    public void log4(String log, Object... arguments) {
        log(Level.FOUR, log, arguments);
    }

    public void log5(String log, Object... arguments) {
        log(Level.FIVE, log, arguments);
    }

    public void log6(String log, Object... arguments) {
        log(Level.SIX, log, arguments);
    }

    public void log7(String log, Object... arguments) {
        log(Level.SEVEN, log, arguments);
    }

    public void log8(String log, Object... arguments) {
        log(Level.EIGHT, log, arguments);
    }

    public void log9(String log, Object... arguments) {
        log(Level.NINE, log, arguments);
    }

    public void logForException(Exception e) {
        logForException(Level.NINE.ordinal(), e);
    }

    public void logForException(int logLevel, Exception e) {

    }

    private boolean isUpperLogLevel(int logLevel) {
        return false;
    }
}