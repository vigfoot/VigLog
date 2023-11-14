package com.vigfoot.setting.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//@Inherited
@Target(ElementType.FIELD)
public @interface Formatter {

    String pattern() default "";

    String dateTime() default "yyyy-MM-dd HH:mm:ss";


}
