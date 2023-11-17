package com.vigfoot.log;

import com.vigfoot.config.DefaultConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassScanner {

    File[] scan() {
        return deepScanClassFile(DefaultConfiguration.CLASS_PATH);
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