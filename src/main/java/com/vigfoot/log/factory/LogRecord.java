package com.vigfoot.log.factory;

import com.vigfoot.exception.VigLogException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord {

    private static LogRecord logRecord;
    private static FileWriter fileWriter;
    private static String absolutePath;
    private static String logFileName;


    private LogRecord() {
    }

    public static LogRecord getLogRecord() {
        if (logRecord == null) {
            logRecord = new LogRecord();
        }
        return logRecord;
    }

    protected void log(final String... msgs) {
        new Thread(new Runnable() {
            public void run() {
                final String logResult = buildLog(msgs);
                writeConsole(logResult);
                writeLogFile(logResult);
            }
        }).start();
    }

    private static synchronized void writeConsole(String logResult) {
        try {
            new PrintStream(System.out, true, "UTF-8").println(logResult);
        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private static synchronized void writeLogFile(String logResult) {
        File currentLogFile = new File(absolutePath + File.separator + logFileName);
        final String lastLogDateFormat = new SimpleDateFormat("_yyyyMMdd")
                .format(new Date(currentLogFile.lastModified()));
        final String currentDateFormat = new SimpleDateFormat("_yyyyMMdd")
                .format(new Date());

        final boolean isOverMidnight = !currentDateFormat.equalsIgnoreCase(lastLogDateFormat);
        if (isOverMidnight) {
            final String recordFileName = absolutePath + File.separator + currentDateFormat + ".log";
            final boolean renameResult = currentLogFile.renameTo(new File(recordFileName));
            if (!renameResult) throw new VigLogException();
        }

        try {
            if (fileWriter == null)
                fileWriter = new FileWriter(logFileName, !isOverMidnight);
            fileWriter.write(logResult);
        } catch (IOException e) {
            throw new VigLogException();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
//                    fileWriter.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    private static String buildLog(String[] msgs) {
        String msgTemplate = msgs[0];

        final int alternateCount = (msgTemplate.length() - msgTemplate.replace("{}", "").length()) / 2;
        final int argumentsCount = msgs.length - 1;
        if (alternateCount != argumentsCount) {
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