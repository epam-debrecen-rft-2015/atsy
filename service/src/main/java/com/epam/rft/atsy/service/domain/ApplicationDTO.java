package com.epam.rft.atsy.service.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private Long id;
    private Date creationDate;
    private Long candidateId;
    private Long positionId;
    private Long channelId;

}
