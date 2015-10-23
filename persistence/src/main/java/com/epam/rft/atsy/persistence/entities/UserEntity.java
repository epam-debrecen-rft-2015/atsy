package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by mates on 10/22/2015.
 */
@Entity
@Table(name = "Users", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class UserEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", table = "Users")
    private long userId;
    @Column(name = "userName", nullable = false, length = 255, table = "Users")
    private String userName;
    @Column(name = "userPwd", nullable = false, length = 255, table = "Users")
    private String userPassword;

    public UserEntity() {
    }

    public UserEntity(long userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public UserEntity(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
