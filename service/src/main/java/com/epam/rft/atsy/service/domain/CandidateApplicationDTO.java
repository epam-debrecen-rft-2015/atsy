package com.epam.rft.atsy.service.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateApplicationDTO {

    private Long lastStateId;
    private Long applicationId;
    private String positionName;
    private String creationDate;
    private String modificationDate;
    private String stateType;

}
