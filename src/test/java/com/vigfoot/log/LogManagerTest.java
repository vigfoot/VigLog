package com.vigfoot.log;

import com.vigfoot.V;
import org.junit.jupiter.api.Test;

class LogManagerTest {

    @Test
    void test(){
        for (String s : LogManager.logConfigMap.keySet()) {
            V.log0("TEST {} ", s);
        }
    }
}