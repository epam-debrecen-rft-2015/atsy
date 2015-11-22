package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.rest.CandidateController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;

/**
 * Created by tothd on 2015. 11. 12..
 */
public class CandidateControllerTest {

    @InjectMocks
    CandidateController candidateController;

    @Mock
    CandidateService candidateService;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadPageTest() {
//
//        //given
//        FilterRequest sortingRequest = new FilterRequest();
//        sortingRequest.setFieldName("name");
//        sortingRequest.setOrder(SortingRequest.Order.ASC);
//        given(candidateService.getAllCandidate(sortingRequest)).willReturn(Arrays.asList(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));
//
//        //when
//        Collection<CandidateDTO> result = candidateController.loadPage("", "ASC", "name");
//
//        //then
//        assertThat(result, containsInAnyOrder(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNullExceptionWhenNullOrderGiven() {

        //given
        given(candidateService.getAllCandidate(new FilterRequest())).willReturn(Arrays.asList(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

        //when
        Collection<CandidateDTO> result = candidateController.loadPage("", null, "name");
    }
}
