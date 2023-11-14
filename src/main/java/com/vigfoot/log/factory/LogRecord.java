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

    protected void log(String log, Object... arguments) {
        final String logResult = buildLog(log, arguments);
        writeConsole(logResult);
        writeLogFile(logResult);
    }

    private synchronized void writeConsole(final String logResult) {
        try {
            new PrintStream(System.out, true, "UTF-8").println(logResult);
        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private synchronized void writeLogFile(final String logResult) {
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
                }
            } catch (IOException ignore) {
            }
        }
    }

    private String buildLog(String log, Object... arguments) {
        if (arguments != null && arguments.length != 0){
            final int alternateCount = (log.length() - log.replace("{}", "").length()) / 2;
            final int argumentsCount = arguments.length;
            if (alternateCount != argumentsCount) {
                final StringBuilder errorMessageBuilder = new StringBuilder();
                errorMessageBuilder.append(log);
                for (int i = 0; i < arguments.length; i++) {
                    errorMessageBuilder.append(", ").append(arguments[i]);
                }

                throw new VigLogException("All alternate characters '{}' must be used"
                        , "\t" + errorMessageBuilder
                        , "\t\t'{}' Count: " + alternateCount
                        , "\t\tArgument Count: " + argumentsCount
                );
            }
        }

        String msgTemplate = log;
        for (int i = 0; i < arguments.length; i++) {
            msgTemplate = msgTemplate.replaceFirst("\\{\\}", String.valueOf(arguments[i]));
        }

        return msgTemplate;
    }

    protected void log(Exception e) {
        e.printStackTrace(System.err);
    }
}