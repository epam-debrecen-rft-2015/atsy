package com.epam.rft.atsy.persistence.request;

import com.google.common.base.MoreObjects;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Builder
@Data
@NoArgsConstructor
public class SearchOptions {


    private String name;
    private String email;
    private String phone;


    public SearchOptions(String name, String email, String phone) {
        this.name = MoreObjects.firstNonNull(name,StringUtils.EMPTY);
        this.email = MoreObjects.firstNonNull(email,StringUtils.EMPTY);
        this.phone = MoreObjects.firstNonNull(phone,StringUtils.EMPTY);
    }
}
