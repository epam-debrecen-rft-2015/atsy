package com.epam.rft.atsy.service.domain;

import lombok.*;

import java.io.Serializable;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO implements Serializable {

    private String newPassword;
    private String newPasswordConfirm;
    private String oldPassword;

    @Override
    public String toString() {
        return "PasswordChangeDTO{FIELDS_HIDDEN_FOR_SECURITY_REASONS}";
    }
}
