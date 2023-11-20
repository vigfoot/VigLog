package com.vigfoot.log;

import com.vigfoot.VigLog;
import com.vigfoot.config.DefaultProperties;
import com.vigfoot.config.ValueObject;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.*;

public class ClassScanner {

    List<Class<?>> scanUserAllClass() {
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        final File[] files = deepScanClassFile(DefaultProperties.USER_DIRECTORY);
        for (File file : files) classList.add(collectClass(file));

        return classList;
    }

    Map<String, ValueObject.LogConfig> filterDeclaredLogClass() {
        final List<Class<?>> scan = scanUserAllClass();
        final Map<String, ValueObject.LogConfig> classList = new HashMap<String, ValueObject.LogConfig>();
        for (Class<?> clazz : scan) {
            final ValueObject.LogConfig logConfig = filterDeclaredLogAnnotation(clazz);
            if (logConfig != null) classList.put(logConfig.getClazz().getName() ,logConfig);
        }
        return classList;
    }

    public static ValueObject.LogConfig filterDeclaredLogAnnotation(Class<?> clazz) {
        final VigLog annotation = clazz.getAnnotation(VigLog.class);
        return annotation != null ? new ValueObject.LogConfig(clazz, annotation) : null;
    }

    private Class<?> collectClass(File file) {
        for (String classpath : DefaultProperties.CLASS_PATH_LIST) {
            if (!file.getAbsolutePath().contains(classpath)) continue;
            String className = file.getAbsolutePath()
                    .replace(classpath, "")
                    .replace(".class", "")
                    .replace(DefaultProperties.FILE_SEPARATOR, ".");

            className = className.indexOf(".") == 0 ? className.substring(1) : className;
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ignore) {
            }
        }
        return null;
    }

    private File[] deepScanClassFile(String parentName) {
        return deepScanClassFile(new File(parentName));
    }

    private File[] deepScanClassFile(File... parents) {
        if (parents == null || parents.length == 0) return null;
        final List<File> scanFiles = new ArrayList<File>();
        for (File parent : parents) {
            if (parent.isDirectory()) {
                final File[] deepScanFile = deepScanClassFile(parent.listFiles());
                if (deepScanFile == null || deepScanFile.length < 1) continue;
                Collections.addAll(scanFiles, deepScanFile);

            } else {
                if (parent.getName().contains(".class"))
                    scanFiles.add(parent);
            }
        }
        return scanFiles.toArray(new File[0]);
    }
}