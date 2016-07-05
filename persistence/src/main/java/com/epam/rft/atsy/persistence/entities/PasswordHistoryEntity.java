package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "password_history", schema = "atsy")
public class PasswordHistoryEntity extends SuperEntity implements Serializable {


    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity userEntity;

    @Column(name ="password", table = "password_history", nullable = false)
    private String password;

    @Column(name ="change_date", table = "password_history", length = 255, nullable = false)
    private Date changeDate;

}
