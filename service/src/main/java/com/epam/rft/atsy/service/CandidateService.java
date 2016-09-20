package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.service.response.PagingResponse;

/**
 * Service that operates with candidates in the database layer and in the view layer.
 */
public interface CandidateService {

  /**
   * Returns the candidate object with the given id.
   * @param candidateID the id of candidate
   * @return the candidate object
   */
  CandidateDTO getCandidate(Long candidateID);

  /**
   * Returns the candidate object wich has an application with the given id.
   * @param applicationID the id of the application
   * @return the candidate object
   */
  CandidateDTO getCandidateByApplicationID(Long applicationID);


  PagingResponse<CandidateDTO> getCandidatesByFilterRequest(
      CandidateFilterRequest candidateFilterRequest);

  void deletePositionsByCandidate(CandidateDTO candidateDTO);

  /**
   * Saves a candidate to the database and returns it's id.
   * @param candidate the candidate
   * @return the id of candidate
   */
  Long saveOrUpdate(CandidateDTO candidate);

  /**
   * Returns the candidate whose email is equal to the specified email. If candidate not exists then
   * returns null.
   * @param email the email of the candidate we're searching for
   * @return the candidate
   */
  CandidateDTO getCandidateDtoByEmail(String email);

  /**
   * Returns the candidate whose phone is equal to the specified phone. If candidate not exists then
   * returns null.
   * @param phone the phone of the candidate we're searching for
   * @return the candidate
   */
  CandidateDTO getCandidateDtoByPhone(String phone);
}