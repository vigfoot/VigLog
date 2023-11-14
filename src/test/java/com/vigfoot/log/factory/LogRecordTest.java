package com.vigfoot.log.factory;

import junit.framework.TestCase;

public class LogRecordTest extends TestCase {


    public void testName() {

        LogRecord.getLogRecord().log("{} hidfasdfasdfsd {}", "sdf","hello");

    }
}