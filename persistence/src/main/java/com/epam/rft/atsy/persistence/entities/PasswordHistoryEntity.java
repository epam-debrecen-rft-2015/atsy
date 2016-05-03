package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    public PasswordHistoryEntity() {
    }

    public PasswordHistoryEntity(Long id, UserEntity userEntity, String password, Date changeDate) {
        super(id);
        this.userEntity = userEntity;
        this.password = password;
        this.changeDate = changeDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PasswordHistoryEntity entity = (PasswordHistoryEntity) o;

        if (userEntity != null ? !userEntity.equals(entity.userEntity) : entity.userEntity != null) return false;
        if (password != null ? !password.equals(entity.password) : entity.password != null) return false;
        return !(changeDate != null ? !changeDate.equals(entity.changeDate) : entity.changeDate != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (changeDate != null ? changeDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordHistoryEntity{" +
                "userEntity=" + userEntity +
                ", password='" + password + '\'' +
                ", changeDate=" + changeDate +
                '}';
    }
}
