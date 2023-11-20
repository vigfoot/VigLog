package com.vigfoot.log;

import com.vigfoot.V;
import com.vigfoot.VigLog;
import org.junit.jupiter.api.Test;


@VigLog(pattern = "#dateTime #msg")
class LogManagerTest {

    @Test
    void test(){
        for (String s : LogManager.logConfigList.keySet()) {
            V.log0("TEST {} ", s);
        }
    }
}