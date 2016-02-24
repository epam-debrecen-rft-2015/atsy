package com.epam.rft.atsy.persistence.request;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

public class SearchOptions {


    private String name;
    private String email;
    private String phone;

    public SearchOptions(String name, String email, String phone) {
        this.name = MoreObjects.firstNonNull(name,StringUtils.EMPTY);
        this.email = MoreObjects.firstNonNull(email,StringUtils.EMPTY);
        this.phone = MoreObjects.firstNonNull(phone,StringUtils.EMPTY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
