package com.vigfoot.log;

import java.util.List;

public class LogManager {

    public void config() {
        final List<Class<?>> classes = new ClassScanner().filterDeclaredLogAnnotation();

    }
}