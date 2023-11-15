package com.vigfoot.log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {

    void scan() {
        final File[] fileList = getFileList(System.getProperty("user.dir"));
//        deepScanFileList()
    }

    private File[] getFileList(String parentName) {
        if (parentName == null || "".equalsIgnoreCase(parentName.trim())) return null;
        return getFileList(new File(parentName));
    }

    private File[] getFileList(File parent) {
        if (parent.listFiles() == null) return null;
        return parent.listFiles();
    }

    private File[] collectClassFileName(File[] files){
        if (files == null || files.length == 0) return null;
        final List<File> classFileList = new ArrayList<File>();

        for (File file : files) {
            if (file.getName().contains(".class"))
                classFileList.add(file);
        }

        return classFileList.toArray(new File[0]);
    }
}