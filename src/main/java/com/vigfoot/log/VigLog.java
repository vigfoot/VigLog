package com.vigfoot.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface VigLog {

    String value() default "";

}