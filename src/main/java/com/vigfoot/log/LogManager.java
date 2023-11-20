package com.vigfoot.log;

import com.vigfoot.config.ValueObject;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class LogManager {

    public void config() {
        final List<ValueObject.LogConfig> classes = new ClassScanner().filterDeclaredLogClass();
        for (ValueObject.LogConfig clazz : classes) {
            System.out.println(clazz);

        }
    }
}