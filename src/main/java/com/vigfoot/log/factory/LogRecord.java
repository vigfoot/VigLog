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
        try {
            final PrintStream print = new PrintStream(System.out, true, "UTF-8");
            String msgTemplate = msgs[0];

            for (int i = 1; i < msgs.length; i++) {
                msgTemplate = msgTemplate.replaceFirst("\\{\\}", msgs[i]);
            }

            if (msgTemplate.contains("{}")) {
                throw new VigLogException("Do Not Leave a Replacement Character '{}'"
                        , '\t' + "Your Log Message"
                        , "\t\t\"" + msgTemplate + "\""
                );
            }
            print.println(msgTemplate);

        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    protected synchronized void log(Exception e) {
        e.printStackTrace(System.err);
    }
}
