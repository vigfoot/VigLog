package com.vigfoot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
public @interface VigLog {

    String pattern() default "[#level #dateTime] #msg";
    String dateTime() default "yyyy-MM-dd HH:mm:ss";
    String logFilePath() default "classpath://";
    String logFileName() default "viglog";

}