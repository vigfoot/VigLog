package com.vigfoot;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VigLog {

    String pattern() default "[#level #dateTime] #msg";
    String dateTime() default "yyyy-MM-dd HH:mm:ss";
    String logFilePath() default "classpath://";
    String logFileName() default "viglog";

}