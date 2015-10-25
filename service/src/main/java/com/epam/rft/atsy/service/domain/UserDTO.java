package com.epam.rft.atsy.service.domain;

import java.io.Serializable;

/**
 * Created by tothd on 2015. 10. 21..
 */
public class UserDTO implements Serializable{

    private Long userID;
    private String name;
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
}
