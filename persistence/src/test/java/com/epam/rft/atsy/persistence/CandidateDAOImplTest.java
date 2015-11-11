package com.epam.rft.atsy.persistence;

import com.epam.rft.atsy.persistence.dao.impl.CandidateDAOImpl;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * Created by szabo on 2015. 11. 11..
 */
public class CandidateDAOImplTest {

    @InjectMocks
    CandidateDAOImpl candidateDAOImpl;

    @Mock
    CandidateEntity candidateEntity;

    @Mock
    CandidateRequestDTO candidateRequestDTO;

    @Mock
    CriteriaBuilder criteriaBuilder;

    @Mock
    CriteriaQuery<CandidateEntity> criteriaQuery;

    @Mock
    CriteriaQuery<CandidateEntity> criteriaQueryAll;

    @Mock
    Root<CandidateEntity> root;

    @Mock
    TypedQuery<CandidateEntity> candidateEntityTypedQuery;

    @Mock
    EntityManager entityManager;

    @Mock
    List<CandidateEntity> ret;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadAllTest(){
        given(candidateRequestDTO.getFieldName()).willReturn("name");
        given(candidateRequestDTO.getOrder()).willReturn(CandidateRequestDTO.Order.ASC);
        //given(candidateRequestDTO.).willReturn(new CandidateRequestDTO.Order[]{CandidateRequestDTO.Order.ASC,CandidateRequestDTO.Order.DESC});
        given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        given(criteriaBuilder.createQuery(CandidateEntity.class)).willReturn(criteriaQuery);
        given(criteriaQuery.from(CandidateEntity.class)).willReturn(root);
        given(criteriaQuery.orderBy(criteriaBuilder.asc(root.get(candidateRequestDTO.getFieldName())))).willReturn(criteriaQuery);
        given(criteriaQuery.select(root)).willReturn(criteriaQueryAll);
        given(entityManager.createQuery(criteriaQueryAll)).willReturn(candidateEntityTypedQuery);
        given(candidateEntityTypedQuery.getResultList()).willReturn(ret);
        Collection<CandidateEntity> resultList = candidateDAOImpl.loadAll(candidateRequestDTO);
        
        assertThat(ret, not(emptyCollectionOf(CandidateEntity.class)));

    }
}
