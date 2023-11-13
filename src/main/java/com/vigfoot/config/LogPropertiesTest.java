package com.vigfoot.config;


import org.junit.Test;

public class LogPropertiesTest {

    @Test
    public void test1() {
        new LogProperties().getLogProperties("/");
    }
}