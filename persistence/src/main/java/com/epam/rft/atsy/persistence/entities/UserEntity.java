package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

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

    /**
     * Empty constructor.
     */
    public UserEntity() {
    }

    /**
     * Full constructor.
     * @param userId is the ID of the user
     * @param userName is the name of the user
     * @param userPassword is the password of the user
     */
    public UserEntity(long userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Constructor for users without id.
     * @param userName is the name of the user
     * @param userPassword is the password of the user
     */
    public UserEntity(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Getter for the userId field.
     * Returns a long value represents the userId.
     * @return long value represents the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for the userId field.
     * @param userId is a long value for the userId field
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Getter for the userName field.
     * Returns a String represents the userName.
     * @return long value represents the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the userName field.
     * @param userName is a String for the userName field
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the userPassword field.
     * Returns a String value represents the userPassword.
     * @return String value represents the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter for the userPassword field.
     * @param userPassword is a String for the userPassword field
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
