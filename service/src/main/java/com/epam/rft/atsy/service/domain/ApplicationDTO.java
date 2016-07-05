package com.epam.rft.atsy.service.domain;


import lombok.*;

import java.util.Date;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ApplicationDTO {

    private Long id;
    private Date creationDate;
    private Long candidateId;
    private Long positionId;
    private Long channelId;

}
