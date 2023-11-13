package com.vigfoot.config;

import com.vigfoot.exception.MESSAGE;
import com.vigfoot.exception.VigLogException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class LogProperties {

    public void getLogProperties(String propertiesDir) {
        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesDir));
        } catch (IOException e) {
            try {
                properties.load(new FileInputStream("./src/main/resources/basics/vig-log.properties"));
            } catch (IOException ignore) {
                throw new VigLogException(MESSAGE.NO_PROP.msg);
            }
        }

        final Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            final String name = (String) enumeration.nextElement();
            System.out.println(name + " " + properties.get(name));
        }
    }
}
