package com.vigfoot.log.factory;

import com.vigfoot.V;
import com.vigfoot.config.DefaultProperties;
import com.vigfoot.exception.VigLogException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord {

    private static LogRecord logRecord;
    private String logMsg;
    private Object[] arguments;
    private FileWriter fileWriter;
    private String absolutePath;
    private String logFileName;
    private boolean useLogFile;
    private String logPattern;
    private String dateTimeFormat;
    private V.Level level;
    private long currentTimeMillis;

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public void setLogPattern(String logPattern) {
        this.logPattern = logPattern;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public void setLogFileName(String logFileName) {
        try {
            this.logFileName = logFileName;
            this.fileWriter = new FileWriter(absolutePath + File.separator + logFileName + ".log", true);
        } catch (IOException e) {
            throw new VigLogException();
        }
    }

    public void setLevel(V.Level level) {
        this.level = level;
    }

    private LogRecord() {
    }

    public static void writeLog(V.Level level, String logPrefix, Object[] arguments) {
        LogRecord.writeLog(level, logPrefix, arguments, null, null, null, null);
    }

    public static void writeLog(V.Level level, String logPrefix, Object[] arguments, String absolutePath, String logFileName, String logPattern, String dateTimeFormat) {
        if (LogRecord.logRecord == null) {
            LogRecord.logRecord = new LogRecord();
        }

        logRecord.setLogMsg(logPrefix);
        logRecord.setArguments(arguments);
        logRecord.setCurrentTimeMillis(System.currentTimeMillis());

        if (level != null) {
            logRecord.setLevel(level);
        } else {
            logRecord.setLevel(DefaultProperties.Log.level);
        }

        if (absolutePath != null) {
            logRecord.setAbsolutePath(absolutePath);
        } else {
            logRecord.setAbsolutePath(DefaultProperties.USER_DIRECTORY);
        }

        if (logFileName != null) {
            logRecord.setLogFileName(logFileName);
        } else {
            logRecord.setLogFileName(DefaultProperties.Log.fileName);
        }

        if (logPattern != null) {
            logRecord.setLogPattern(logPattern);
        } else {
            logRecord.setLogPattern(DefaultProperties.Log.pattern);
        }

        if (dateTimeFormat != null) {
            logRecord.setDateTimeFormat(dateTimeFormat);
        } else {
            logRecord.setDateTimeFormat(DefaultProperties.Log.dateTime);
        }

        final String logResult = logRecord.buildLog();
        logRecord.writeConsole(logResult);
        logRecord.writeLogFile(logResult);
    }

    protected void logStackTrace(int level, Exception e) {
        e.printStackTrace(System.err);
    }

    private synchronized void writeConsole(final String logResult) {
        try {
            new PrintStream(System.out, true, "UTF-8").print(logResult);
        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private synchronized void writeLogFile(String logResult) {
        final File currentLogFile = new File(absolutePath + File.separator + logFileName + ".log");
        if (currentLogFile.exists()) {
            final String lastLogDateFormat = new SimpleDateFormat("_yyyyMMdd")
                    .format(new Date(currentLogFile.lastModified()));
            final String currentDateFormat = new SimpleDateFormat("_yyyyMMdd")
                    .format(new Date(currentTimeMillis));

            final boolean isOverMidnight = !currentDateFormat.equalsIgnoreCase(lastLogDateFormat);
            if (isOverMidnight) {
                final String recordFileName = absolutePath + File.separator + currentDateFormat + ".log";
                final boolean renameResult = currentLogFile.renameTo(new File(recordFileName));
                if (!renameResult) throw new VigLogException();
            }
        }

        try {
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

    private String buildLog() {
        final String dateformat = new SimpleDateFormat(dateTimeFormat).format(new Date(currentTimeMillis));
        String msgTemplate = logPattern
                .replace("#level", String.valueOf(level.prefix()))
                .replace("#dateTime", dateformat)
                .replace("#msg", logMsg)
                .replace("#nextLine", DefaultProperties.TEXT_NEXT_LINE)
                ;

        if (arguments != null && arguments.length > 0) {
            final int alternateCount = (logMsg.length() - logMsg.replace("{}", "").length()) / 2;
            final int argumentsCount = arguments.length;
            if (alternateCount != argumentsCount) {
                final StringBuilder errorMessageBuilder = new StringBuilder();
                errorMessageBuilder.append(logMsg);
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