package com.epam.rft.atsy.cucumber.welcome;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateData {
  String candidateName;
  Long candidateID;
  String position;
}
