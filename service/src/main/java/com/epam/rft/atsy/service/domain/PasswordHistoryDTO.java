package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import java.util.Date;

public class PasswordHistoryDTO implements Serializable {

    private Long changeId;

    private Long userId;

    private String password;

    private Date changeDate;

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
}
