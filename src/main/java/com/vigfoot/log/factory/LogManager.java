package com.vigfoot.log.factory;

import com.vigfoot.log.VigLog;
import com.vigfoot.log.config.DefaultProperties;
import com.vigfoot.log.config.ValueObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@VigLog
public class LogManager {

    protected static Map<String, ValueObject.LogConfig> logConfigMap = new HashMap<String, ValueObject.LogConfig>();
    protected static ExecutorService pool;

    static {
        final Map<String, ValueObject.LogConfig> declaredLogClass = ClassScanner.getInstance().filterDeclaredLogClass();
        pool = Executors.newSingleThreadExecutor();
        for (Map.Entry<String, ValueObject.LogConfig> configEntry : declaredLogClass.entrySet()) {
            final String className = configEntry.getKey();
            final ValueObject.LogConfig logConfig = configEntry.getValue();
            registerClasConfig(className, logConfig);
        }
    }

    protected static void registerClasConfig(String className, ValueObject.LogConfig classConfig) {
        final VigLog config = classConfig.getVigLog();
        final String pattern = config.pattern();
        final String logFileName = config.fileName();
        final int logLevel = config.level() > 9 ? 0 : config.level();
        final String logFilePath = config.filePath();
        final String dateTime = config.dateTime();

        final LogRecord.LEVEL level = LogRecord.LEVEL.values()[logLevel];
        final LogRecord logRecord = LogRecord.getLogRecord(level, pattern, dateTime, logFilePath, logFileName);
        classConfig.setLogRecord(logRecord);

        logConfigMap.put(className, classConfig);
    }

    public static ValueObject.LogConfig getClassConfig(String className) {
        return logConfigMap.get(className != null ? className : DefaultProperties.logManagerClass.getName());
    }

    public static ExecutorService getPool() {
        return pool;
    }
}