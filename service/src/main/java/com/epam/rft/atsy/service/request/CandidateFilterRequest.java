package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateFilterRequest extends SortingAndPagingRequest {
  String candidateName;
  String candidateEmail;
  String candidatePhone;
  String candiadtePositions;
}
