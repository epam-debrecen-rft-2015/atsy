package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("classpath:sql/passwordhistory/passwordhistory.sql")
public class PasswordHistoryRepositoryIT extends AbstractRepositoryIT {

    private static final String TEST_USERNAME_WITHOUT_PASSWORD_HISTORY = "Test2";
    private static final String TEST_USERNAME_WITH_SINGLE_PASSWORD_HISTORY = "Test";
    private static final String TEST_USERNAME_WITH_THREE_PASSWORD_HISTORY = "Dev";
    private static final String TEST_PASSWORD_1_FOR_USER_WITH_THREE_PASSWORD_HISTORY = "password_history1";
    private static final String TEST_PASSWORD_2_FOR_USER_WITH_THREE_PASSWORD_HISTORY = "password_history2";

    private static final String ACTUAL_PASSWORD_FOR_USER_WITH_THREE_PASSWORD_HISTORY = "$2a$04$QSJkUouEDGfbWAtuxnlf/.Ajen6lviIhrNwKFPPZ.juRn6nLgvBi6";

    @Autowired
    private PasswordHistoryRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUserEntityShouldNotFindPasswordHistoryForUserWithoutPasswordHistory() {
        //Given
        UserEntity user = this.userRepository.findByUserName(TEST_USERNAME_WITHOUT_PASSWORD_HISTORY);

        //When
        List<PasswordHistoryEntity> result = this.repository.findByUserEntity(user);

        //Then
        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

    @Test
    public void findByUserEntityShouldFindSinglePasswordHistoryForUserWithSinglePasswordHistory() {
        //Given
        UserEntity user = this.userRepository.findByUserName(TEST_USERNAME_WITH_SINGLE_PASSWORD_HISTORY);
        Date nearNow = currentDateMinus(5);

        //When
        List<PasswordHistoryEntity> result = this.repository.findByUserEntity(user);

        //Then
        assertThat(result, notNullValue());
        assertThat(result.size(), is(1));
        assertPasswordHistoryEntity(result.get(0), user, user.getUserPassword(), nearNow);
    }

    @Test
    public void findByUserEntityShouldFindThreePasswordHistoryForUserWithThreePasswordHistory() {
        //Given
        UserEntity user = this.userRepository.findByUserName(TEST_USERNAME_WITH_THREE_PASSWORD_HISTORY);
        Date nearNow = currentDateMinus(5);

        //When
        List<PasswordHistoryEntity> result = this.repository.findByUserEntity(user);

        //Then
        assertThat(result, notNullValue());
        assertThat(result.size(), is(3));

        assertPasswordHistoryEntity(result.get(0),
                user,
                user.getUserPassword(),
                nearNow);

        assertPasswordHistoryEntity(result.get(1),
                user,
                TEST_PASSWORD_1_FOR_USER_WITH_THREE_PASSWORD_HISTORY,
                nearNow);

        assertPasswordHistoryEntity(result.get(2),
                user,
                TEST_PASSWORD_2_FOR_USER_WITH_THREE_PASSWORD_HISTORY,
                nearNow);

    }

    @Test
    public void findTop1ByUserEntityIdOrderByChangeDateShouldNotFindPasswordHistoryForUserWithoutPasswordHistory() {
        //Given
        Long testUserIdWithoutPasswordHistory = this.userRepository.findByUserName(TEST_USERNAME_WITHOUT_PASSWORD_HISTORY).getId();

        //When
        PasswordHistoryEntity pwHistory = this.repository.findTop1ByUserEntityIdOrderByChangeDate(testUserIdWithoutPasswordHistory);

        //Then
        assertThat(pwHistory, nullValue());
    }

    @Test
    public void findTop1ByUserEntityIdOrderByChangeDateShouldfindTop1ByUserEntityIdOrderByChangeDateHistoryForUserWithThreePasswordHistory() {
        //Given
        UserEntity user = this.userRepository.findByUserName(TEST_USERNAME_WITH_THREE_PASSWORD_HISTORY);
        Long testUserIdWithThreePasswordHistory = user.getId();
        Date nearNow = currentDateMinus(5);

        //When
        PasswordHistoryEntity pwHistory = this.repository.findTop1ByUserEntityIdOrderByChangeDate(testUserIdWithThreePasswordHistory);

        //Then
        assertThat(pwHistory, notNullValue());
        assertPasswordHistoryEntity(pwHistory, user, ACTUAL_PASSWORD_FOR_USER_WITH_THREE_PASSWORD_HISTORY, nearNow);
    }

    private void assertPasswordHistoryEntity(PasswordHistoryEntity actual,
                                             UserEntity expectedUserEntity,
                                             String expectedPassword,
                                             Date currentDate) {

        assertThat(actual, notNullValue());

        assertThat(actual.getUserEntity(), notNullValue());
        assertThat(actual.getUserEntity(), is(expectedUserEntity));

        assertThat(actual.getPassword(), notNullValue());
        assertThat(actual.getPassword(), is(expectedPassword));

        assertThat(actual.getChangeDate(), notNullValue());
        assertThat(actual.getChangeDate(), greaterThan(currentDate));

    }

    private Date currentDateMinus(long seconds) {
        return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
    }

}
