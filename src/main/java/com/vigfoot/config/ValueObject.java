package com.vigfoot.config;

import com.vigfoot.VigLog;

public class ValueObject {

    public static class LogConfig {
        private Class<?> clazz;
        private VigLog vigLog;

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

        @Override
        public String toString() {
            return "LogConfig{" +
                    "clazz=" + clazz +
                    ", vigLog=" + vigLog +
                    '}';
        }
    }
}