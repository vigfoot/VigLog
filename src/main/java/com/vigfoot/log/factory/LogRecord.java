package com.vigfoot.log.factory;

import com.vigfoot.V;
import com.vigfoot.config.DefaultProperties;
import com.vigfoot.config.ValueObject;
import com.vigfoot.exception.VigLogException;
import com.vigfoot.log.LogManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord extends Thread {

    public enum Level {
        ZERO("L0"), ONE("L1"), TWO("L2"), THREE("L3"), FOUR("L4"), FIVE("L5"), SIX("L6"), SEVEN("L7"), EIGHT("L8"), NINE("L9");

        final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

        public String prefix() {
            return prefix;
        }
    }

    private String logMsg;
    private Object[] arguments;
    private FileWriter fileWriter;
    private String absolutePath;
    private String logFileName;
    private String logPattern;
    private String dateTimeFormat;
    private long currentTimeMillis;
    private Level level;
    private Level defaultLevel;


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

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public void setLogFileWriter(String absolutePath, String logFileName) {
        try {
            this.absolutePath = absolutePath;
            this.logFileName = logFileName;
            this.fileWriter = new FileWriter(absolutePath + File.separator + logFileName + ".log", true);
        } catch (IOException e) {
            throw new VigLogException();
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    protected void setDefaultLevel(Level defaultLevel) {
        this.defaultLevel = defaultLevel;
    }

    private LogRecord() {
    }


    @Override
    public void run() {
        final String logResult = buildLog();
        writeConsole(logResult);
        if (absolutePath != null) writeLogFile(logResult);
    }

    public static void writeLog(Level level, String logMsg, Object[] arguments) {
        final LogRecord logRecord = getLogRecord();
        if (!logRecord.isUpperLogLevel(level)) return;

        logRecord.setLevel(level);
        logRecord.setLogMsg(logMsg);
        logRecord.setArguments(arguments);
        logRecord.setCurrentTimeMillis(System.currentTimeMillis());

        LogManager.getPool().execute(logRecord);
    }

    public static LogRecord getLogRecord() {
        return getLogRecord(null, null, null, null, null);
    }

    public static LogRecord getLogRecord(Level defaultLevel, String logPattern, String dateTimeFormat, String absolutePath, String logFileName) {
        LogRecord logRecord;
        final String callerClassName = getCallerClassName();
        ValueObject.LogConfig classConfig = LogManager.getClassConfig(callerClassName);

        if (classConfig == null) {
            logRecord = new LogRecord();
            logRecord.setDefaultLevel(defaultLevel);
            logRecord.setLogPattern(logPattern);
            logRecord.setDateTimeFormat(dateTimeFormat);

            if (absolutePath != null && absolutePath.trim().length() != 0) { // TODO: master config 파일로 연동 필요
                logRecord.setLogFileWriter(absolutePath, logFileName != null ? logFileName : DefaultProperties.LOG_NAME);
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
            if (className.contains("com.vigfoot") && !className.toLowerCase().contains("test")/*TODO 테스트 임시*/) continue;

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
                .replace("#newLine", DefaultProperties.TEXT_NEXT_LINE);

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

    private boolean isUpperLogLevel(Level logLevel) {
        return logLevel.ordinal() >= this.defaultLevel.ordinal();
    }
}