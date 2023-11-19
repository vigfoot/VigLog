package com.vigfoot.log;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class LogManager {

    public void config() {
        final List<Class<?>> classes = new ClassScanner().filterDeclaredLogAnnotation();

    }
}