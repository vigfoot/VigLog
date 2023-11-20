package com.vigfoot.config;

import com.vigfoot.V;

public class DefaultProperties {

    public static String THREAD_NAME = "viglog";
    public static String LOG_PACKAGE = "com.vigfoot.VigLog";
    public static String USER_DIRECTORY = System.getProperty("user.dir");
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    public static String TEXT_NEXT_LINE = System.getProperty("line.separator");
    public static String[] CLASS_PATH_LIST = System.getProperty("java.class.path").split(";");

    public static class Log {
        public static String pattern = "[#level #dateTime] #msg#nextLine";
        public static String dateTime = "yyyy-MM-dd HH:mm:ss";
        public static String fileName = "viglog";
        public static V.Level level = V.Level.ZERO;

    }
}