package com.epam.rft.atsy.service.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserDTO implements Serializable{

    private Long userID;

    @NotNull
    private String name;

    @NotNull
    private String password;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (userID != null ? !userID.equals(userDTO.userID) : userDTO.userID != null) return false;
        if (name != null ? !name.equals(userDTO.name) : userDTO.name != null) return false;
        return !(password != null ? !password.equals(userDTO.password) : userDTO.password != null);

    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
