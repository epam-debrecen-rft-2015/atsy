package com.epam.rft.atsy.service.exception;

public class DuplicateRecordException extends RuntimeException {
    private final String name;

    public DuplicateRecordException(String name, String message, Throwable cause) {
        super(message, cause);

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
