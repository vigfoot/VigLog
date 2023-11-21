package com.vigfoot.log.factory;

import com.vigfoot.log.V;
import com.vigfoot.log.VigLog;
import com.vigfoot.log.config.DefaultProperties;
import com.vigfoot.log.config.ValueObject;

import java.io.File;
import java.util.*;

public class ClassScanner {

    private static List<Class<?>> scan;

    static List<Class<?>> scanUserAllClass() {
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        final File[] files = deepScanClassFile(DefaultProperties.USER_DIRECTORY);
        for (File file : files) {
            final Class<?> aClass = collectClass(file);
            if (aClass != null) classList.add(aClass);
        }

        return classList;
    }

    static Map<String, ValueObject.LogConfig> filterDeclaredLogClass() {
        scan = scanUserAllClass();

        final Map<String, ValueObject.LogConfig> classList = new HashMap<String, ValueObject.LogConfig>();
        for (Class<?> clazz : scan) {
            final ValueObject.LogConfig logConfig = filterDeclaredLogAnnotation(clazz);
            if (logConfig != null) classList.put(logConfig.getClazz().getName(), logConfig);
        }

        final Class<?> userConfigClass = scanUserConfigClass(scan);
        if (userConfigClass != null) {
            final ValueObject.LogConfig logConfig = filterDeclaredLogAnnotation(userConfigClass);
            if (logConfig != null) {
                classList.put(DefaultProperties.logManagerClass.getName(), logConfig);
                return classList;
            }
        }

        final ValueObject.LogConfig logConfig = filterDeclaredLogAnnotation(DefaultProperties.logManagerClass);
        classList.put(logConfig.getClazz().getName(), logConfig);

        return classList;
    }

    static Class<?> scanUserConfigClass(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (DefaultProperties.logManagerClass.getName().equals(clazz.getSuperclass().getName()))
                return clazz;
        }
        return null;
    }

    private static ValueObject.LogConfig filterDeclaredLogAnnotation(Class<?> clazz) {
        final VigLog annotation = clazz.getAnnotation(VigLog.class);
        return annotation != null ? new ValueObject.LogConfig(clazz, annotation) : null;
    }

    private static Class<?> collectClass(File file) {
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

    private static File[] deepScanClassFile(String parentName) {
        return deepScanClassFile(new File(parentName));
    }

    private static File[] deepScanClassFile(File... parents) {
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