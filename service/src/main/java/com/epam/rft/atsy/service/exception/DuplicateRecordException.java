package com.epam.rft.atsy.service.exception;

/**
 * Created by Ikantik on 2015.11.04..
 */
public class DuplicateRecordException extends RuntimeException {
    private final String name;

    public DuplicateRecordException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
