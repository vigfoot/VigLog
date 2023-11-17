package com.vigfoot.log;

import junit.framework.TestCase;

import java.io.File;

public class ClassScannerTest extends TestCase {

    public void testScan() {
        final File[] scan = new ClassScanner().scan();
        for (File file : scan) {
            System.out.println(file.getName());

        }
    }
}