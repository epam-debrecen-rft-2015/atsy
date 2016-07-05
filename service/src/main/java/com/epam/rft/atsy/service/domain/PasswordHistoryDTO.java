package com.epam.rft.atsy.service.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PasswordHistoryDTO implements Serializable {


    private Long id;
    private Long userId;

    private String password;
    private Date changeDate;

}
