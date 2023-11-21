package com.vigfoot.log.factory;

import com.vigfoot.log.VigLog;
import com.vigfoot.log.config.DefaultProperties;
import com.vigfoot.log.config.ValueObject;

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
            final String className = logConfig.getClazz().getName();
            registerClasConfig(className, logConfig);
        }
    }

    protected static void registerClasConfig(String className, ValueObject.LogConfig classConfig) {
        final VigLog config = classConfig.getVigLog();
        final String pattern = config.pattern();
        final String logFileName = config.logFileName();
        final int logLevel = config.logLevel() > 9 ? 0 : config.logLevel();
        final String logFilePath = config.logFilePath();
        final String dateTime = config.dateTime();

        final LogRecord.Level level = LogRecord.Level.values()[logLevel];
        final LogRecord logRecord = LogRecord.getLogRecord(level, pattern, dateTime, logFilePath, logFileName);
        classConfig.setLogRecord(logRecord);

        logConfigMap.put(className, classConfig);
        pool.execute(classConfig.getLogRecord());
    }

    public static ValueObject.LogConfig getClassConfig(String className) {
        return logConfigMap.get(className != null ? className : DefaultProperties.VIGLOG_CLASS_NAME);
    }

    public static ExecutorService getPool() {
        return pool;
    }
}