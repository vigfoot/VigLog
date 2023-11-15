package com.vigfoot.log.factory;

interface LogInterface {

    void log0(String log, Object... arguments);

    void log1(String log, Object... arguments);

    void log2(String log, Object... arguments);

    void log3(String log, Object... arguments);

    void log4(String log, Object... arguments);

    void log5(String log, Object... arguments);

    void log6(String log, Object... arguments);

    void log7(String log, Object... arguments);

    void log8(String log, Object... arguments);

    void log9(String log, Object... arguments);

    void logStackTrace(int logLevel, Exception e);

}