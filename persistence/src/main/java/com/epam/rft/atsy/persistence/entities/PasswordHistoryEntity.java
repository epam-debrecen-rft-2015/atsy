package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "password") @Entity
@Table(name = "password_history", schema = "atsy") public class PasswordHistoryEntity
    extends SuperEntity implements Serializable {


    @ManyToOne(targetEntity = UserEntity.class) @JoinColumn(name = "users_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "password", table = "password_history", nullable = false) private String
        password;

    @Column(name = "change_date", table = "password_history", length = 255, nullable = false)
    private Date changeDate;


    @Builder
    public PasswordHistoryEntity(Long id, UserEntity userEntity, String password, Date changeDate) {
        super(id);
        this.userEntity = userEntity;
        this.password = password;
        this.changeDate = changeDate;
    }

}
