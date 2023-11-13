package com.vigfoot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class LogProperties {

    void test() throws IOException {
        final Properties properties = new Properties();

        final File file = new File("./");
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getName());
        }

        properties.load(new FileInputStream(new File("./src/main/resources/basics/vig-log.properties")));

        final Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            final String name = (String) enumeration.nextElement();
            System.out.println(name + ' ' + properties.get(name));

        }
    }
}
