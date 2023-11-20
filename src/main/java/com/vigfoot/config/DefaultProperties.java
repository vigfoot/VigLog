package com.vigfoot.config;

public class DefaultProperties {

    public static String THREAD_NAME = "viglog";
    public static String LOG_PACKAGE = "com.vigfoot.VigLog";
    public static String USER_DIRECTORY = System.getProperty("user.dir");
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    public static String[] CLASS_PATH_LIST = System.getProperty("java.class.path").split(";");

}