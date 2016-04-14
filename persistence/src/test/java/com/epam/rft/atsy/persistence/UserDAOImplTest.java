package com.epam.rft.atsy.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAOImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImplTest.class);
    private static final String UNAME = "username";
    private static final String PWD = "password";
/*
    @InjectMocks
    UserDAOImpl userDAOImpl;

    @Mock
    UserEntity userEntity;

    @Mock
    CriteriaBuilder criteriaBuilder;

    @Mock
    CriteriaQuery<UserEntity> criteriaQuery;

    @Mock
    Root<UserEntity> root;

    @Mock
    TypedQuery<UserEntity> userEntityTypedQuery;

    @Mock
    EntityManager entityManager;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginTest() {

        //given
        String username = "userName";
        String password = "userPassord";
        given(userEntity.getUserName()).willReturn(username);
        given(userEntity.getUserPassword()).willReturn(password);
        given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        given(criteriaBuilder.createQuery(UserEntity.class)).willReturn(criteriaQuery);
        given(criteriaQuery.from(UserEntity.class)).willReturn(root);
        given(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(UNAME), username),
            criteriaBuilder.equal(root.get(PWD), password)))).willReturn(criteriaQuery);
        given(entityManager.createQuery(criteriaQuery)).willReturn(userEntityTypedQuery);
        given(userEntityTypedQuery.getSingleResult()).willReturn(userEntity);
        UserEntity resultUserEntity = userDAOImpl.login(username, password);

        assertThat(resultUserEntity.getUserName(), is(equalToIgnoringCase(username)));
        assertThat(resultUserEntity.getUserPassword(), is(equalToIgnoringCase(password)));

    }
*/
    //TODO
    //NoResultException - if there is no result
    //NonUniqueResultException - if more than one result

}
