package com.vigfoot.log;

import junit.framework.TestCase;

public class ClassScannerTest extends TestCase {

    public void testScan() {
        new ClassScanner().scan();
    }
}