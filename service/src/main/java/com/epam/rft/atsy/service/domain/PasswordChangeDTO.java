package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO implements Serializable {

    private String newPassword;
    private String newPasswordConfirm;
    private String oldPassword;

}
