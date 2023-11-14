package com.vigfoot;

import com.vigfoot.log.factory.Logger;
import junit.framework.TestCase;

public class LogRunnerTest extends TestCase {

    public void testName() {
        new Logger().log0("{}.sdfasdfsa {}");

    }
}