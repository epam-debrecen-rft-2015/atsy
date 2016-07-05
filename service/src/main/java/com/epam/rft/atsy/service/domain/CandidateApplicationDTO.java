package com.epam.rft.atsy.service.domain;


import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CandidateApplicationDTO {

    private Long lastStateId;
    private Long applicationId;
    private String positionName;
    private String creationDate;
    private String modificationDate;
    private String stateType;

}
