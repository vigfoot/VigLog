package com.vigfoot;

public class Test {


    public static void main(String[] args) {
        for (Class<?> aClass : VigLog.class.getDeclaredClasses()) {
            System.out.println(aClass.getName());
        }


    }
}
