package com.vigfoot;

enum PropSet {
    pattern("[#level #dateTime] #msg"), dateTime("yyyy-MM-dd HH:mm:ss"), logPath("classpath://");

    final String val;

    PropSet(String val) {
        this.val = val;
    }
}
