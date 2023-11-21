package com.vigfoot.log.exception;

public class VigLogException extends RuntimeException {

    public VigLogException(String message) {
        super(message);
    }

    public VigLogException(String... messages) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0, messagesLength = messages.length; i < messagesLength; i++) {
            String message = messages[i];
            builder.append(message);
            if (i != messagesLength - 1) builder.append(System.getProperty("line.separator"));
        }

        throw new VigLogException(builder.toString());
    }
}