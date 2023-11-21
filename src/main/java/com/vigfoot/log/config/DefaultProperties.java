package com.vigfoot.log.config;

import com.vigfoot.log.V;

public class DefaultProperties {

    public static String LOG_NAME = "viglog";
    public static String LOG_PACKAGE = "com.vigfoot.log";
    public static String VIGLOG_CLASS_NAME = V.class.getName();
    public static String USER_DIRECTORY = System.getProperty("user.dir");
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    public static String TEXT_NEXT_LINE = System.getProperty("line.separator");
    public static String[] CLASS_PATH_LIST = System.getProperty("java.class.path").split(";");
}