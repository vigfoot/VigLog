package com.vigfoot.config;

public class DefaultConfiguration {

    public static String USER_DIRECTORY = System.getProperty("user.dir");
    public static String[] CLASS_PATH_LIST = System.getProperty("java.class.path").split(";");

}