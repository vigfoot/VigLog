package java.com.vigfoot.config;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class VigLoggerTest {

    @Test
    public void test() throws IOException {
        final Properties properties = new Properties();
        properties.load(new FileInputStream("com.vigfoot.basics.vig-log.properties"));

        final Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            final String name = (String) enumeration.nextElement();
            System.out.println(name + ' ' + properties.get(name));

        }
    }
}