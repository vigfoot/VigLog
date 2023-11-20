package com.vigfoot.config;

import com.vigfoot.VigLog;
import com.vigfoot.log.factory.LogRecord;

public class ValueObject {

    public static class LogConfig {
        private Class<?> clazz;
        private VigLog vigLog;
        private LogRecord logRecord;

        public LogConfig(Class<?> clazz, VigLog vigLog) {
            this.clazz = clazz;
            this.vigLog = vigLog;
        }

        public VigLog getVigLog() {
            return vigLog;
        }

        public void setVigLog(VigLog vigLog) {
            this.vigLog = vigLog;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public LogRecord getLogRecord() {
            return logRecord;
        }

        public void setLogRecord(LogRecord logRecord) {
            this.logRecord = logRecord;
        }

        @Override
        public String toString() {
            return "LogConfig{" +
                    "clazz=" + clazz +
                    ", vigLog=" + vigLog +
                    ", logRecord=" + logRecord +
                    '}';
        }
    }
}