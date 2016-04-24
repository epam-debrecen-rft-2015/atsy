package com.epam.rft.atsy.service.domain;

import java.io.Serializable;

public class PasswordChangeDTO implements Serializable {

    private String newPassword;

    private String newPasswordConfirm;

    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordChangeDTO that = (PasswordChangeDTO) o;

        if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) return false;
        if (newPasswordConfirm != null ? !newPasswordConfirm.equals(that.newPasswordConfirm) : that.newPasswordConfirm != null)
            return false;
        return !(oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null);

    }

    @Override
    public int hashCode() {
        int result = newPassword != null ? newPassword.hashCode() : 0;
        result = 31 * result + (newPasswordConfirm != null ? newPasswordConfirm.hashCode() : 0);
        result = 31 * result + (oldPassword != null ? oldPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordChangeDTO{" +
                "newPassword='" + newPassword + '\'' +
                ", newPasswordConfirm='" + newPasswordConfirm + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                '}';
    }
}
