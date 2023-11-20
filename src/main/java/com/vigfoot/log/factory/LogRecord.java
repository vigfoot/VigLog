package com.vigfoot.log.factory;

import com.vigfoot.config.DefaultProperties;
import com.vigfoot.exception.VigLogException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord extends Thread {

    private static LogRecord logRecord;

    private String logPrefix;
    private Object[] arguments;
    private FileWriter fileWriter;
    private String absolutePath;
    private String logFileName;

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    private LogRecord() {
    }

    public static void writeLog(String logPrefix, Object[] arguments) {
        LogRecord.writeLog(logPrefix, arguments, null, null);
    }

    public static void writeLog(String logPrefix, Object[] arguments, String absolutePath, String logFileName) {
        if (LogRecord.logRecord == null){
            LogRecord.logRecord = new LogRecord();
            LogRecord.logRecord.setDaemon(true);
            LogRecord.logRecord.setName(DefaultProperties.THREAD_NAME);
        }

        logRecord.setLogPrefix(logPrefix);
        logRecord.setArguments(arguments);

        if (absolutePath != null) {
            logRecord.setAbsolutePath(absolutePath);
        } else {
            logRecord.setAbsolutePath(absolutePath);
        }

        if (logFileName != null) {
            logRecord.setLogFileName(logFileName);
        } else {
            logRecord.setLogFileName(logFileName);
        }

        LogRecord.logRecord.start();
    }

    @Override
    public void run() {
        final String logResult = buildLog(logPrefix, arguments);
        writeConsole(logResult);
        writeLogFile(logResult);
    }

    protected void logStackTrace(int level, Exception e) {
        e.printStackTrace(System.err);
    }

    private synchronized void writeConsole(final String logResult) {
        try {
            new PrintStream(System.out, true, "UTF-8").println(logResult);
        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private synchronized void writeLogFile(String logResult) {
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
        String msgTemplate = log;
        if (arguments != null && arguments.length > 0) {
            final int alternateCount = (log.length() - log.replace("{}", "").length()) / 2;
            final int argumentsCount = arguments.length;
            if (alternateCount != argumentsCount) {
                final StringBuilder errorMessageBuilder = new StringBuilder();
                errorMessageBuilder.append(log);
                for (Object argument : arguments) {
                    errorMessageBuilder.append(", ").append(argument);
                }

                throw new VigLogException("All alternate characters '{}' must be used"
                        , "\t" + errorMessageBuilder
                        , "\t\t'{}' Count: " + alternateCount
                        , "\t\tArgument Count: " + argumentsCount
                );
            }
            for (Object argument : arguments) {
                msgTemplate = msgTemplate
                        .replaceFirst("\\{\\}", String.valueOf(argument));
            }
        }

        return msgTemplate;
    }
}