package com.vigfoot.log;

import java.lang.annotation.Annotation;
import java.util.List;

public class LogManager {

    public Class<?> filterDeclaredLogAnnotation(){
        final List<Class<?>> scan = new ClassScanner().scan();

        for (Class<?> clazz : scan) {
            final Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                System.out.println(annotation.annotationType().getName());
            }
        }
        return null;
    }
}
