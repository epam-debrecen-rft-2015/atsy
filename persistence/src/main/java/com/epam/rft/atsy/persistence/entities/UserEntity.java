package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

/**
 * Created by mates on 10/22/2015.
 */
@Entity
@Table(name="Users", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class UserEntity implements java.io.Serializable {

    private long userId;
    private String userName;
    private String userPassword;

    public UserEntity() {
    }

    public UserEntity(long userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "userId", table = "Users")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "userName", nullable = false, length = 255, table = "Users")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "userPwd", nullable = false, length = 255, table = "Users")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
