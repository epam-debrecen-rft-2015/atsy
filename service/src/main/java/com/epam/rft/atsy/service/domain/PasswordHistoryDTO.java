package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordHistoryDTO implements Serializable {


    private Long id;
    private Long userId;

    private String password;
    private Date changeDate;

}
