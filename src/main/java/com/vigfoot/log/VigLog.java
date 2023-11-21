package com.vigfoot.log;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VigLog {

    String pattern() default "[#level] #dateTime #msg #newLine";
    String dateTime() default "yy-MM-dd HH:mm:ss";
    int level() default 0;
    String filePath() default "";
    String fileName() default "viglog";

}