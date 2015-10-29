package com.epam.rft.atsy.persistence;

import com.epam.rft.atsy.persistence.dao.UserDAO;
import com.epam.rft.atsy.persistence.dao.impl.UserDAOImpl;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
/**
 * Created by mates on 10/29/2015.
 */
public class UserDAOImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImplTest.class);
    private static final String UNAME="username";
    private static final String PWD="password";

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
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginTest(){

        //given
        String username="userName";
        String password="userPassord";
        given(userEntity.getUserName()).willReturn("userName");
        given(userEntity.getUserPassword()).willReturn("userPassword");
        given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        given(criteriaBuilder.createQuery(UserEntity.class)).willReturn(criteriaQuery);
        given(criteriaQuery.from(UserEntity.class)).willReturn(root);
        given(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(UNAME), username),
                criteriaBuilder.equal(root.get(PWD), password)))).willReturn(criteriaQuery);
        given(entityManager.createQuery(criteriaQuery)).willReturn(userEntityTypedQuery);

        UserEntity resultUserEntity = userDAOImpl.login(username,password);

        assertThat(resultUserEntity.getUserName(), is(equalToIgnoringCase("userName")));
        assertThat(resultUserEntity.getUserPassword(), is(equalToIgnoringCase("passWord")));

    }


    //TODO
    //NoResultException - if there is no result
    //NonUniqueResultException - if more than one result

}
