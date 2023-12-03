package com.vigfoot.log.factory;

import com.vigfoot.log.config.DefaultProperties;
import com.vigfoot.log.config.ValueObject;
import com.vigfoot.log.exception.VigLogException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord implements Runnable {

    public enum LEVEL {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
    }

    private String logMsg;
    private Object[] arguments;
    private FileWriter fileWriter;
    private String fileDateFormat;
    private String absolutePath;
    private String logFileName;
    private String logPattern;
    private String dateTimeFormat;
    private Long currentTimeMillis;
    private LEVEL level;
    private LEVEL defaultLevel;


    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public void setLogPattern(String logPattern) {
        this.logPattern = logPattern;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public void setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public void setLogFileFormat(String absolutePath, String logFileName) {
        this.absolutePath = absolutePath;
        this.logFileName = logFileName;
    }

    public void setLevel(LEVEL level) {
        this.level = level;
    }

    protected void setDefaultLevel(LEVEL defaultLevel) {
        this.defaultLevel = defaultLevel;
    }

    private LogRecord() {
    }

    public void run() {
        final String logResult = buildLog();
        writeConsole(logResult);
        if (absolutePath != null) writeLogFile(logResult);
    }

    public static void writeLog(LEVEL level, String logMsg, Object[] arguments) {
        final LogRecord logRecord = getLogRecord();
        if (!logRecord.isUpperLogLevel(level)) return;

        logRecord.setLevel(level);
        logRecord.setLogMsg(logMsg);
        logRecord.setArguments(arguments);
        logRecord.setCurrentTimeMillis(System.currentTimeMillis());

        LogManager.getPool().execute(logRecord);
    }

    public static LogRecord getLogRecord() {
        final String callerClassName = getCallerClassName();
        ValueObject.LogConfig classConfig = LogManager.getClassConfig(callerClassName);
        if (classConfig == null) {
            classConfig = LogManager.getClassConfig(DefaultProperties.logManagerClass.getName());
            LogManager.registerClasConfig(callerClassName, classConfig);
        }

        return classConfig.getLogRecord();
    }

    protected static LogRecord getLogRecord(LEVEL defaultLevel, String logPattern, String dateTimeFormat, String absolutePath, String logFileName) {
        LogRecord logRecord;
        ValueObject.LogConfig classConfig = LogManager.getClassConfig(getCallerClassName());

        if (classConfig == null) {
            logRecord = new LogRecord();
            logRecord.setDefaultLevel(defaultLevel);
            logRecord.setLogPattern(logPattern);
            logRecord.setDateTimeFormat(dateTimeFormat);

            if (absolutePath != null && absolutePath.trim().length() != 0) {
                logRecord.setLogFileFormat(absolutePath, logFileName != null ? logFileName : DefaultProperties.LOG_NAME);
            }

        } else {
            logRecord = classConfig.getLogRecord();

        }
        return logRecord;
    }

    public static String getCallerClassName() {
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            final String className = stackTraceElement.getClassName();
            if (className.contains(DefaultProperties.LOG_PACKAGE)) continue;

            return className;
        }
        return null;
    }

    private void writeConsole(final String logResult) {
        try {
            new PrintStream(System.out, true, "UTF-8").print(logResult);
        } catch (UnsupportedEncodingException e) {
            throw new VigLogException("Unsupported Encoding UTF-8", "Check Your JDK Version");
        }
    }

    private void writeLogFile(String logResult) {
        final String currentDateFormat = new SimpleDateFormat("_yyyyMMdd")
                .format(new Date(currentTimeMillis));
        try {
            if (!currentDateFormat.equals(this.fileDateFormat)) {
                this.fileDateFormat = currentDateFormat;
                this.fileWriter = new FileWriter(this.absolutePath + File.separator + this.logFileName + currentDateFormat + ".log", true);
            }

            this.fileWriter.write(logResult);
            this.fileWriter.flush();
        } catch (IOException e) {
            throw new VigLogException();
        }
    }

    private String buildLog() {
        final String dateformat = new SimpleDateFormat(dateTimeFormat).format(new Date(currentTimeMillis));
        String msgTemplate = logPattern;

        if (msgTemplate.contains("#level"))
            msgTemplate = msgTemplate.replace("#level", String.valueOf(level.ordinal()));

        if (msgTemplate.contains("#dateTime"))
            msgTemplate = msgTemplate.replace("#dateTime", dateformat);

        if (msgTemplate.contains("#msg"))
            msgTemplate = msgTemplate.replace("#msg", logMsg);

        if (msgTemplate.contains("#newLine"))
            msgTemplate = msgTemplate.replace("#newLine", DefaultProperties.TEXT_NEXT_LINE);

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

    private boolean isUpperLogLevel(LEVEL logLevel) {
        return logLevel.ordinal() >= this.defaultLevel.ordinal();
    }
}