package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class UserEntity extends SuperEntity implements java.io.Serializable{


    @Column(name = "user_name", nullable = false, length = 255, table = "users")
    private String userName;

    @Column(name = "user_pwd", nullable = false, length = 255, table = "users")
    private String userPassword;


    public UserEntity(Long id, String userName, String userPassword) {
        super(id);
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
