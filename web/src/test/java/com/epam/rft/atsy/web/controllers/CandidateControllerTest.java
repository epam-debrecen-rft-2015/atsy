package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.rest.CandidateController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

public class CandidateControllerTest {

    @InjectMocks
    CandidateController candidateController;

    @Mock
    CandidateService candidateService;

    @Mock
    ObjectMapper objectMapper;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /*@Test
    public void loadPageTest() throws IOException {

        //given
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFieldName("name");
        filterRequest.setOrder(SortingRequest.Order.ASC);
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("name","test");
        filterRequest.setFilters(filterMap);
        given(candidateService.getAllCandidate(filterRequest)).willReturn(Arrays.asList(new CandidateDTO("test", "email", "phone", "description", "referer", new Short("1"))));
        String filterJson="{\"name\":\"test\"}";
        given(objectMapper.readValue(filterJson,Map.class)).willReturn(filterMap);

        //when
        Collection<CandidateDTO> result = candidateController.loadPage(filterJson, "ASC", "name");

        //then
        assertThat(result, containsInAnyOrder(new CandidateDTO("test", "email", "phone", "description", "referer", new Short("1"))));
    }*/

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNullExceptionWhenNullOrderGiven() {

        //given
        given(candidateService.getAllCandidate(new FilterRequest())).willReturn(Arrays.asList(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

        //when
        Collection<CandidateDTO> result = candidateController.loadPage("", null, "name");
    }

    /*@Test
    public void loadPageIOExceptionTest() throws IOException {

        //given
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFieldName("name");
        filterRequest.setOrder(SortingRequest.Order.ASC);
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("name","test");
        filterRequest.setFilters(filterMap);
        given(candidateService.getAllCandidate(filterRequest)).willReturn(Arrays.asList(new CandidateDTO("test", "email", "phone", "description", "referer", new Short("1"))));
        String filterJson="{\"name\":\"test\"}";
        given(objectMapper.readValue(filterJson,Map.class)).willThrow(new IOException("IOexception"));

        //when
        Collection<CandidateDTO> result = candidateController.loadPage(filterJson, "ASC", "name");

        //then
        assertThat(result, is(empty()));
    }*/

    /*@Test
    public void loadPageIOStringUtilIsEmpty() throws IOException {

        //given
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setFieldName("name");
        filterRequest.setOrder(SortingRequest.Order.ASC);
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("name","test");
        filterRequest.setFilters(filterMap);
        given(candidateService.getAllCandidate(filterRequest)).willReturn(Arrays.asList(new CandidateDTO("test", "email", "phone", "description", "referer", new Short("1"))));
        String filterJson="";
        //given(objectMapper.readValue(filterJson,Map.class)).willThrow(new IOException("IOexception"));

        //when
        Collection<CandidateDTO> result = candidateController.loadPage(filterJson, "ASC", "name");

        //then
        assertThat(result, is(empty()));
    }*/
}
