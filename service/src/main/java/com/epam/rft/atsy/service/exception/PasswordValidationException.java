package com.epam.rft.atsy.service.exception;

import java.util.ResourceBundle;

public class PasswordValidationException extends Exception {
    private String messageKey;

    public PasswordValidationException() {
    }

    public PasswordValidationException(String messageKey) {
        // Both getMessage() and getMessageKey() will return the message key.
        // getMessageKey() should be used when it helps understanding by its semantics.
        super(messageKey);

        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
