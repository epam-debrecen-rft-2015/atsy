package com.epam.rft.atsy.web.controllers;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.rest.SingleCandidateController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.mockito.BDDMockito.given;

/**
 * Created by mates on 2015. 11. 25..
 */
public class SingleCandidateControllerTest {
    @InjectMocks
    private SingleCandidateController underTest;

    @Mock
    CandidateDTO candidateDTO;
    @Mock
    BindingResult bindingResult;
    @Mock
    BindingResult bindingResultTrue;
    @Mock
    ResponseEntity responseEntity;
    @Mock
    ResponseEntity responseEntityTrue;
    @Mock
    CandidateService candidateService;
    @Mock
    MessageSource messageSource;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveOrUpdate(){
        Locale locale=new Locale("hu");
        given(bindingResult.hasErrors()).willReturn(false);
        given(bindingResultTrue.hasErrors()).willReturn(true);
        given(candidateDTO.getCandidateId()).willReturn(new Long(1));
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDTO.getEmail()).willReturn("candidate.a@atsy.com");
        given(candidateDTO.getPhone()).willReturn("+36105555555");
        given(candidateDTO.getReferer()).willReturn("google");
        given(candidateDTO.getDescription()).willReturn("Elegáns, kicsit furi");
        given(candidateDTO.getLanguageSkill()).willReturn((short)5);
        given(candidateService.saveOrUpdate(candidateDTO)).willReturn(new Long(1));
        given(responseEntity.getStatusCode()).willReturn(HttpStatus.OK);
        given(responseEntity.getBody()).willReturn(new Long(1));
        given(responseEntityTrue.getStatusCode()).willReturn(HttpStatus.BAD_REQUEST);
        given(responseEntityTrue.getBody()).willReturn(new Long(1));
        Long candidateId=candidateService.saveOrUpdate(candidateDTO);
        ResponseEntity entity = underTest.saveOrUpdate(candidateDTO,bindingResult,locale);
        ResponseEntity entityTrue = underTest.saveOrUpdate(candidateDTO,bindingResultTrue,locale);

        assertThat(entity.getStatusCode(),is(HttpStatus.OK));
        assertThat(entityTrue.getStatusCode(),is(HttpStatus.BAD_REQUEST));
    }
}
