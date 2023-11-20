package com.vigfoot.log.factory;

import com.vigfoot.V;
import org.junit.jupiter.api.Test;

import java.util.Date;

class LogRecordTest {

    @Test
    void test(){
        final Date date = new Date(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            V.log6("string {}", "hi");
        }
        final Date date1 = new Date(System.currentTimeMillis());
        System.out.println(date);
        System.out.println(date1);
    }
}