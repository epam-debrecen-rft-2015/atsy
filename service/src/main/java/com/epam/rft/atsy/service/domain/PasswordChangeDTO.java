package com.epam.rft.atsy.service.domain;

import lombok.*;

import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PasswordChangeDTO implements Serializable {

    private String newPassword;
    private String newPasswordConfirm;
    private String oldPassword;

}
