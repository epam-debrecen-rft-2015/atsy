package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PasswordHistory", schema = "atsy")
public class PasswordHistoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "changeId", table = "PasswordHistory")
    private Long changeId;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "userId", nullable = false)
    private Long userId;

    @Column(name ="password", table = "PasswordHistory", nullable = false)
    private String password;

    @Column(name ="changeData", table = "PasswordHistory", length = 255, nullable = false)
    private Date changeDate;

    public PasswordHistoryEntity() {
    }

    public PasswordHistoryEntity(Long changeId, Long userId, String password, Date changeDate) {
        this.changeId = changeId;
        this.userId = userId;
        this.password = password;
        this.changeDate = changeDate;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        PasswordHistoryEntity that = (PasswordHistoryEntity) o;

        if (changeId != null ? !changeId.equals(that.changeId) : that.changeId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return !(changeDate != null ? !changeDate.equals(that.changeDate) : that.changeDate != null);

    }

    @Override
    public int hashCode() {
        int result = changeId != null ? changeId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (changeDate != null ? changeDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordHistoryEntity{" +
                "changeId=" + changeId +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", changeDate=" + changeDate +
                '}';
    }
}
