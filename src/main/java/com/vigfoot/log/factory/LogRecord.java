package com.vigfoot.log.factory;

import com.vigfoot.exception.VigLogException;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class LogRecord {

    private static LogRecord logRecord;

    private LogRecord() {
    }

    public static LogRecord getLogRecord() {
        if (logRecord == null) {
            logRecord = new LogRecord();
        }
        return logRecord;
    }

    protected synchronized void log(String... msgs) {
        final String logResult = buildLog(msgs);

        try {
            new PrintStream(System.out, true, "UTF-8").println(logResult);

        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private static String buildLog(String[] msgs) {
        String msgTemplate = msgs[0];

        final int alternateCount = (msgTemplate.length() - msgTemplate.replace("{}", "").length()) / 2;
        final int argumentsCount = msgs.length - 1;
        if (alternateCount != argumentsCount){
            final StringBuilder errorMessageBuilder = new StringBuilder();
            errorMessageBuilder.append(msgs[0]);
            for (int i = 1; i < msgs.length; i++) {
                errorMessageBuilder.append(", ").append(msgs[i]);
            }

            throw new VigLogException("All alternate characters '{}' must be used"
                    , "\t" + errorMessageBuilder
                    , "\t\t'{}' Count: " + alternateCount
                    , "\t\tArgument Count: " + argumentsCount
            );
        }

        for (int i = 1; i < msgs.length; i++) {
            msgTemplate = msgTemplate.replaceFirst("\\{\\}", msgs[i]);
        }

        return msgTemplate;
    }

    protected synchronized void log(Exception e) {
        e.printStackTrace(System.err);
    }
}