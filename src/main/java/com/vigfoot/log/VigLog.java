package com.vigfoot.log;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VigLog {

    String pattern() default "[#level] #dateTime #msg #newLine";
    String dateTime() default "yy-MM-dd HH:mm:ss";
    int logLevel() default 0;
    String logFilePath() default "";
    String logFileName() default "viglog";

}