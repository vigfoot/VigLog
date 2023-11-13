package com.vigfoot.test;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws Exception {
        final Properties properties = new Properties();
        properties.load(new FileInputStream("com.vigfoot.basics.vig-log.properties"));

        final Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            final String name = (String) enumeration.nextElement();
            System.out.println(name + ' ' + properties.get(name));

        }
    }
}
