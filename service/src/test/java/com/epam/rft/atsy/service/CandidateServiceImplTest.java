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
import static org.hamcrest.Matchers.is;
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

    @Mock
    CandidateDTO candidateDTO;

    @Mock
    CandidateEntity candidateEntity;

    @Mock
    CandidateEntity candidateEntity2;

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

    @Test
    public void updateTest(){
        Long id=new Long(1);
        given(candidateDTO.getCandidateId()).willReturn(id);
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDTO.getEmail()).willReturn("candidate.a@atsy.com");
        given(candidateDTO.getPhone()).willReturn("+36105555555");
        given(candidateDTO.getReferer()).willReturn("google");
        given(candidateDTO.getDescription()).willReturn("Elegáns, kicsit furi");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(modelMapper.map(candidateDTO,CandidateEntity.class)).willReturn(candidateEntity);
        given(candidateEntity.getCandidateId()).willReturn(id);
        given(candidateDAO.update(candidateEntity)).willReturn(candidateEntity);
        given(candidateDAO.update(candidateEntity).getCandidateId()).willReturn(id);

        Long candidateId=candidateService.saveOrUpdate(candidateDTO);

        assertThat(candidateId, is(id));

    }

    @Test
    public void saveTest(){
        Long id=new Long(1);
        given(candidateDTO.getCandidateId()).willReturn(null);
        given(candidateDTO.getName()).willReturn("test");
        given(candidateDTO.getEmail()).willReturn("test@test.com");
        given(candidateDTO.getPhone()).willReturn("+36105555557");
        given(candidateDTO.getReferer()).willReturn("fb");
        given(candidateDTO.getDescription()).willReturn("test");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(candidateEntity2.getCandidateId()).willReturn(id);
        given(candidateEntity2.getName()).willReturn("test");
        given(candidateEntity2.getEmail()).willReturn("test@test.com");
        given(candidateEntity2.getPhone()).willReturn("+36105555557");
        given(candidateEntity2.getReferer()).willReturn("fb");
        given(candidateEntity2.getDescription()).willReturn("test");
        given(candidateEntity2.getLanguageSkill()).willReturn((short) 5);
        given(modelMapper.map(candidateDTO,CandidateEntity.class)).willReturn(candidateEntity);
        given(candidateDAO.create(candidateEntity)).willReturn(candidateEntity2);
        given(candidateEntity.getCandidateId()).willReturn(null);

        given(candidateDAO.create(candidateEntity).getCandidateId()).willReturn(id);

        Long candidateId=candidateService.saveOrUpdate(candidateDTO);

        assertThat(candidateId, is(id));

    }

}
