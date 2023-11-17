package com.vigfoot.log;

import junit.framework.TestCase;

import java.util.List;

public class ClassScannerTest extends TestCase {

    public void testScan() {
        final List<Class<?>> scan = new ClassScanner().scan();
        for (Class<?> aClass : scan) {
            System.out.println(aClass);
        }
    }
}