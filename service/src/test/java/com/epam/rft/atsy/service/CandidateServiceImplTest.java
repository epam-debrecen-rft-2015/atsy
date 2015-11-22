package com.epam.rft.atsy.service;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.impl.CandidateServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;


/**
 * Created by tothd on 2015. 11. 12..
 */
public class CandidateServiceImplTest {

    @InjectMocks
    CandidateServiceImpl candidateService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    CandidateDAO candidateDAO;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCandidates() {

        //given
        FilterRequest sortingRequest = new FilterRequest();
        given(candidateDAO.loadAll(sortingRequest)).willReturn(Arrays.asList(new CandidateEntity("name", "email", "phome", "description", "referer", new Short("1"))));
        given(modelMapper.map(Matchers.any(Collection.class), Matchers.any(Type.class)))
                .willReturn(Arrays.asList(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

        //when
        Collection<CandidateDTO> result = candidateService.getAllCandidate(sortingRequest);

        //then
        assertThat(result, containsInAnyOrder(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

    }

}
