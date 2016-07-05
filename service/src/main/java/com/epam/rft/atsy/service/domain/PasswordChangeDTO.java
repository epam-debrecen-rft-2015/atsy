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

}
