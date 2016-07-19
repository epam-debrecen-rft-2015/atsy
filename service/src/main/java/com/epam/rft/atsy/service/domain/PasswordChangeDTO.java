package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
