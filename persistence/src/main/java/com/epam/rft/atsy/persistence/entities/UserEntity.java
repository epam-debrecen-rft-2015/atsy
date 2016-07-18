package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "userPassword") @Entity
@Table(name = "users", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class UserEntity extends SuperEntity implements java.io.Serializable {


    @Column(name = "user_name", nullable = false, length = 255, table = "users") private String
        userName;

    @Column(name = "user_pwd", nullable = false, length = 255, table = "users") private String
        userPassword;


    @Builder public UserEntity(Long id, String userName, String userPassword) {
        super(id);
        this.userName = userName;
        this.userPassword = userPassword;
    }

}
