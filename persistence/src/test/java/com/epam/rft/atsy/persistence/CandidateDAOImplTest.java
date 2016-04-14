package com.epam.rft.atsy.persistence;

public class CandidateDAOImplTest {

    /*@InjectMocks
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

    }*/
}
