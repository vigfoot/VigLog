package com.vigfoot.exception;

public class VigLogException extends RuntimeException {

    public VigLogException(String message) {
        super(message);
    }

    public VigLogException(String... messages) {
        final StringBuilder builder = new StringBuilder();

        for (String message : messages) {
            builder.append(message).append(System.getProperty("line.separator"));
        }

        throw new VigLogException(builder.toString());
    }
}
