package com.vigfoot.log;

import com.vigfoot.config.DefaultConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassScanner {

    List<Class<?>> scan() {
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        final File[] files = deepScanClassFile(DefaultConfiguration.USER_DIRECTORY);
        for (File file : files) classList.add(collectClass(file));

        return classList;
    }

    private Class<?> collectClass(File file) {
        for (String classpath : DefaultConfiguration.CLASS_PATH_LIST) {
            if (!file.getAbsolutePath().contains(classpath)) continue;
            String className = file.getAbsolutePath()
                    .replace(classpath, "")
                    .replace(".class", "")
                    .replace(DefaultConfiguration.FILE_SEPARATOR, ".");

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