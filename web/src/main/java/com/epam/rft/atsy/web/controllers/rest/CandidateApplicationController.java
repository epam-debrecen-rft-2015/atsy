package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.response.PagingResponse;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import javax.annotation.Resource;

/**
 * A REST controller that is responsible for providing every information about the applications of
 * the candidates.
 */
@RestController
@RequestMapping(value = "/secure/applications/{candidateId}")
public class CandidateApplicationController {

  private static final String APPLICATION_STATE = "candidate.table.state.";

  @Autowired
  private ApplicationsService applicationsService;

  @Resource
  private MessageKeyResolver messageKeyResolver;

  /**
   * Loads and returns all applications of the candidate with the specified identifier, using the
   * specified language.
   * @param candidateId the identifier of the candidate whose applications will be given back
   * @param locale specifies to language to use
   * @return a collection with all applications of the specified candidate
   */
  @RequestMapping(method = RequestMethod.GET)
  public PagingResponse<CandidateApplicationDTO> loadApplications(
      @PathVariable(value = "candidateId") Long candidateId, Locale locale,
      @RequestParam(name = "pageNumber") Integer pageNumber,
      @RequestParam(name = "pageSize") Integer pageSize) {

    PagingResponse<CandidateApplicationDTO>
        candidateApplicationDTOPagingResponse =
        applicationsService.getApplicationsByCandidateId(candidateId, pageNumber - 1, pageSize);

    for (CandidateApplicationDTO candidateApplicationDTO : candidateApplicationDTOPagingResponse
        .getRows()) {
      String stateType = candidateApplicationDTO.getStateType();

      candidateApplicationDTO.setStateType(
          messageKeyResolver.resolveMessageOrDefault(APPLICATION_STATE + stateType, stateType));
    }
    return candidateApplicationDTOPagingResponse;
  }
}
