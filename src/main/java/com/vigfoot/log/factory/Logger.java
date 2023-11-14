package com.vigfoot.log.factory;


public class Logger implements LogInterface {

    enum Level {
        ZERO("[Log 0] ")
        , ONE("[Log 1] ")
        , TWO("[Log 2] ")
        , THREE("[Log 3] ")
        , FOUR("[Log 4] ")
        , FIVE("[Log 5] ")
        , SIX("[Log 6] ")
        , SEVEN("[Log 7] ")
        , EIGHT("[Log 8] ")
        , NINE("[Log 9] ");

        final String prefix;
        Level(String prefix) {
            this.prefix = prefix;
        }
        
    }

    private final LogRecord logRecord = LogRecord.getLogRecord();

    public void log0(String... logMessages) {
        final String[] logs = new String[logMessages.length];
        logs[0] = Level.ZERO.prefix + logMessages[0];

        System.arraycopy(logMessages, 1, logs, 1, logMessages.length - 1);
        
        logRecord.log(logs);
    }

    public void log1(String... logMessages) {

    }

    public void log2(String... logMessages) {

    }

    public void log3(String... logMessages) {

    }

    public void log4(String... logMessages) {

    }

    public void log5(String... logMessages) {

    }

    public void log6(String... logMessages) {

    }

    public void log7(String... logMessages) {

    }

    public void log8(String... logMessages) {

    }

    public void log9(String... logMessages) {

    }

    public void logForException(Exception e) {
        logForException(9, e);
    }

    public void logForException(int logLevel, Exception e) {

    }

    private boolean isUpperLogLevel(int logLevel) {
        return false;
    }
}