package com.vigfoot.log;

import com.vigfoot.VigLog;
import com.vigfoot.config.DefaultProperties;
import com.vigfoot.config.ValueObject;
import com.vigfoot.log.factory.LogRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogManager {

    protected static Map<String, ValueObject.LogConfig> logConfigMap = new HashMap<String, ValueObject.LogConfig>();
    protected static ExecutorService pool;

    static {
        final Map<String, ValueObject.LogConfig> declaredLogClass = ClassScanner.filterDeclaredLogClass();
        pool = Executors.newFixedThreadPool(declaredLogClass.keySet().size());
        for (ValueObject.LogConfig logConfig : declaredLogClass.values()) {
            final VigLog config = logConfig.getVigLog();
            final String pattern = config.pattern();
            final String logFileName = config.logFileName();
            final int logLevel = config.logLevel() > 9 ? 0 : config.logLevel();
            final String logFilePath = config.logFilePath();
            final String dateTime = config.dateTime();

            final LogRecord.Level level = LogRecord.Level.values()[logLevel];
            final LogRecord logRecord = LogRecord.getLogRecord(level, pattern, dateTime, logFilePath, logFileName);
            logConfig.setLogRecord(logRecord);

            final String className = logConfig.getClazz().getName();
            logConfigMap.put(className, logConfig);
            pool.execute(logConfig.getLogRecord());
        }
    }

    public static ValueObject.LogConfig getClassConfig(String className) {
        return logConfigMap.get(className != null ? className : DefaultProperties.LOG_PACKAGE);
    }

    public static ExecutorService getPool() {
        return pool;
    }
}