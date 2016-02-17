package com.epam.rft.atsy.service.exception;

public class DuplicateRecordException extends RuntimeException {
    private final String name;

    public DuplicateRecordException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
