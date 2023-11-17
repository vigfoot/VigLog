package com.vigfoot.log;

import org.junit.jupiter.api.Test;


class LogManagerTest {

    @Test
    void test(){
        new LogManager().filterDeclaredLogAnnotation();
    }
}