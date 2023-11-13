package com.vigfoot.exception;

public enum MESSAGE {

    NO_PROP("Not Found Your \"vig-log.properties\"")
    ,
    ;

    MESSAGE(String msg) {
        this.msg = msg;
    }

    public String msg;
}