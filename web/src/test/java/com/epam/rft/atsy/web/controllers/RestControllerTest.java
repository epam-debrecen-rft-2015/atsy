package com.epam.rft.atsy.web.controllers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

/**
 * Created by mates on 11/1/2015.
 */
public class RestControllerTest {

    @InjectMocks
    RestController restController;

    @Mock
    RestController.Model model;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getModelTest(){
        String attr1 = "ssfsdfsf";
        given(model.getAttr1()).willReturn(attr1);
        RestController.Model model2 = restController.getModel();

        assertThat(model.getAttr1(),is(equalToIgnoringCase(model2.getAttr1())) );
    }
}
