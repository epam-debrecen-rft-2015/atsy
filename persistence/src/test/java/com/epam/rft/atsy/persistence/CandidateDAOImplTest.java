package com.epam.rft.atsy.persistence;

import com.epam.rft.atsy.persistence.dao.impl.CandidateDAOImpl;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;

public class CandidateDAOImplTest {

    @InjectMocks
    CandidateDAOImpl candidateDAOImpl;

    @Mock
    CandidateEntity candidateEntity;

    @Mock
    SortingRequest sortingRequest;

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
        given(sortingRequest.getFieldName()).willReturn("name");
        given(sortingRequest.getOrder()).willReturn(SortingRequest.Order.ASC);
        given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        given(criteriaBuilder.createQuery(CandidateEntity.class)).willReturn(criteriaQuery);
        given(criteriaQuery.from(CandidateEntity.class)).willReturn(root);
        given(criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortingRequest.getFieldName())))).willReturn(criteriaQuery);
        given(criteriaQuery.select(root)).willReturn(criteriaQueryAll);
        given(entityManager.createQuery(criteriaQueryAll)).willReturn(candidateEntityTypedQuery);
        given(candidateEntityTypedQuery.getResultList()).willReturn(ret);
        Collection<CandidateEntity> resultList = candidateDAOImpl.loadAll(sortingRequest);
        
        assertThat(resultList, not(emptyCollectionOf(CandidateEntity.class)));

    }
}
