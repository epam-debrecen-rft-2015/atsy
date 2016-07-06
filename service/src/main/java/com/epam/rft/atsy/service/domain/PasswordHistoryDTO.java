package com.epam.rft.atsy.service.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@ToString(exclude="password")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordHistoryDTO implements Serializable {


    private Long id;
    private Long userId;

    private String password;
    private Date changeDate;

}
