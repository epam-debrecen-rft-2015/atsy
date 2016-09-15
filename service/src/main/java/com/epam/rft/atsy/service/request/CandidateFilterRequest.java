package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateFilterRequest extends SortingAndPagingRequest {
  private String candidateName;
  private String candidateEmail;
  private String candidatePhone;
  private String candiadtePositions;

  @Builder
  public CandidateFilterRequest(String sortName, String sortOrder, Integer pageSize,
                                Integer pageNumber, String candidateName, String candidateEmail,
                                String candidatePhone, String candiadtePositions) {

    super(sortName, sortOrder, pageSize, pageNumber);
    this.candidateName = candidateName;
    this.candidateEmail = candidateEmail;
    this.candidatePhone = candidatePhone;
    this.candiadtePositions = candiadtePositions;
  }
}