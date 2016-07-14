package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.PasswordHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeServiceImplTest {

    private static final int PASSWORD_HISTORY_LIMIT = 5;
    private static final Long USER_ID = 1L;
    private static final UserEntity NULL_USER_ENTITY = null;
    private static final List<PasswordHistoryEntity> EMPTY_PASSWORD_HISTORY_ENTITY_LIST = Collections.emptyList();
    private static final PasswordHistoryEntity EMPTY_PASSWORD_HISTORY_ENTITY = new PasswordHistoryEntity();

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordHistoryRepository passwordHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordChangeServiceImpl passwordChangeService;

    private PasswordHistoryDTO passwordHistoryDtoWithUserId;
    private PasswordHistoryEntity passwordHistoryEntityWithUserEntity;
    private UserEntity userEntityWithId;
    public static final String PASSWORD_PREFIX = "password";

    @Before
    public void setUp() {
        passwordHistoryDtoWithUserId = PasswordHistoryDTO.builder()
                .userId(USER_ID)
                .build();

        passwordHistoryEntityWithUserEntity = PasswordHistoryEntity.builder()
                .userEntity(userEntityWithId)
                .build();

        userEntityWithId = UserEntity.builder()
                .id(USER_ID)
                .build();

        given(this.modelMapper.map(passwordHistoryDtoWithUserId, PasswordHistoryEntity.class)).willReturn(passwordHistoryEntityWithUserEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIAEWhenPasswordHistoryDtoIsNull() {
        //Given

        //When
        this.passwordChangeService.saveOrUpdate(null);

        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIAEWhenPasswordHistoryUserIdIsNull() {
        //Given
        PasswordHistoryDTO passwordHistoryDTO = PasswordHistoryDTO.builder()
                .userId(null)
                .build();

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDTO);

        //Then
    }

    @Test(expected = DataAccessException.class)
    public void saveOrUpdateDelegateDataAccessExceptionFromUserRepository() {
        //Given
        given(this.userRepository.findOne(USER_ID)).willThrow(DataIntegrityViolationException.class);

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIAEWhenUserCannotBeFoundById() {
        // Given
        given(this.userRepository.findOne(USER_ID)).willReturn(NULL_USER_ENTITY);
        given(this.passwordHistoryRepository.findByUserEntity(NULL_USER_ENTITY)).willThrow(IllegalArgumentException.class);

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);
    }

    @Test(expected = DuplicateRecordException.class)
    public void saveOrUpdateShouldThrowDuplicateRecordExceptionBecauseOfConstraintViolationException() {
        //Given
        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.save(passwordHistoryEntityWithUserEntity)).willThrow(ConstraintViolationException.class);

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
    }

    @Test(expected = DuplicateRecordException.class)
    public void saveOrUpdateShouldThrowDuplicateRecordExceptionBecauseOfDataIntegrityViolationException() {
        //Given
        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.save(passwordHistoryEntityWithUserEntity)).willThrow(DataIntegrityViolationException.class);

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
    }

    @Test
    public void saveOrUpdateShouldDeleteOldestPasswordWhenPasswordHistoryLongerThanLimit() {
        //Given

        List<PasswordHistoryEntity> passwordHistoryEntityList = getPasswordHistoryEntitylistWithElements(PASSWORD_HISTORY_LIMIT + 1);

        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.save(passwordHistoryEntityWithUserEntity)).willReturn(passwordHistoryEntityWithUserEntity);
        given(this.passwordHistoryRepository.findByUserEntity(userEntityWithId)).willReturn(passwordHistoryEntityList);
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willReturn(passwordHistoryEntityList.get(0));

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
        then(this.passwordHistoryRepository).should().delete(passwordHistoryEntityList.get(0));
    }

    @Test
    public void saveOrUpdateShouldNotDeleteOldestPasswordWhenPasswordHistoryShorterThanLimit() {
        //Given
        List<PasswordHistoryEntity> passwordHistoryEntityList = getPasswordHistoryEntitylistWithElements(PASSWORD_HISTORY_LIMIT - 1);

        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.save(passwordHistoryEntityWithUserEntity)).willReturn(passwordHistoryEntityWithUserEntity);
        given(this.passwordHistoryRepository.findByUserEntity(userEntityWithId)).willReturn(passwordHistoryEntityList);
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willReturn(passwordHistoryEntityList.get(0));

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
        then(this.passwordHistoryRepository).should(never()).delete(passwordHistoryEntityList.get(0));
    }

    @Test
    public void saveOrUpdateShouldNotDeleteOldestPasswordWhenPasswordHistoryEqualToLimit() {
        //Given
        List<PasswordHistoryEntity> passwordHistoryEntityList = getPasswordHistoryEntitylistWithElements(PASSWORD_HISTORY_LIMIT);

        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.save(passwordHistoryEntityWithUserEntity)).willReturn(passwordHistoryEntityWithUserEntity);
        given(this.passwordHistoryRepository.findByUserEntity(userEntityWithId)).willReturn(passwordHistoryEntityList);
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willReturn(passwordHistoryEntityList.get(0));

        //When
        this.passwordChangeService.saveOrUpdate(passwordHistoryDtoWithUserId);

        //Then
        then(this.passwordHistoryRepository).should(never()).delete(passwordHistoryEntityList.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOldPasswordShouldThrowIAEWhenUserIdIsNull() {
        // Given

        // When
        this.passwordChangeService.getOldPasswords(null);

        // Then
    }

    @Test(expected = DataAccessException.class)
    public void getOldPasswordShouldDelegateDataAccessExceptionFromUserRepository() {
        // Given
        given(this.userRepository.findOne(USER_ID)).willThrow(DataIntegrityViolationException.class);

        // When
        this.passwordChangeService.getOldPasswords(USER_ID);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOldPasswordShouldThrowIAEWhenUserCannotBeFoundById() {
        // Given
        given(this.userRepository.findOne(USER_ID)).willReturn(NULL_USER_ENTITY);
        given(this.passwordHistoryRepository.findByUserEntity(NULL_USER_ENTITY)).willThrow(IllegalArgumentException.class);

        // When
        this.passwordChangeService.getOldPasswords(USER_ID);

        // Then
    }

    @Test
    public void getOldPasswordShouldReturnEmptyListWhenNoPasswordHistoryHasBeenFound() {
        // Given
        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.findByUserEntity(userEntityWithId)).willReturn(EMPTY_PASSWORD_HISTORY_ENTITY_LIST);

        // When
        List<String> result = this.passwordChangeService.getOldPasswords(USER_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.userRepository).should().findOne(USER_ID);
        then(this.passwordHistoryRepository).should().findByUserEntity(userEntityWithId);
    }

    @Test
    public void getOldPasswordShouldExtractPasswordFromHistoryWhenThereIsPasswordHistory() {
        // Given
        final List<PasswordHistoryEntity> passwordHistoryEntityList = getPasswordHistoryEntitylistWithElements(2);
        final List<String> expectedPasswords = Arrays.asList(
                passwordHistoryEntityList.get(0).getPassword(),
                passwordHistoryEntityList.get(1).getPassword()
        );

        given(this.userRepository.findOne(USER_ID)).willReturn(userEntityWithId);
        given(this.passwordHistoryRepository.findByUserEntity(userEntityWithId)).willReturn(passwordHistoryEntityList);

        // When
        List<String> result = this.passwordChangeService.getOldPasswords(USER_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(false));
        assertThat(result, equalTo(expectedPasswords));

        then(this.userRepository).should().findOne(USER_ID);
        then(this.passwordHistoryRepository).should().findByUserEntity(userEntityWithId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteOldestPasswordShouldThrowIAEWhenUserIdIsNull() {
        // Given

        // When
        this.passwordChangeService.deleteOldestPassword(null);

        // Then
    }

    @Test(expected = DataAccessException.class)
    public void deleteOldestPasswordShouldDelegateDataAccessExceptionWhenQueryingOldestPassword() {
        // Given
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willThrow(DataIntegrityViolationException.class);

        // When
        this.passwordChangeService.deleteOldestPassword(USER_ID);

        // Then
    }

    @Test(expected = DataAccessException.class)
    public void deleteOldestPasswordShouldDelegateDataAccessExceptionWhenDeletingHistoryEntity() {
        // Given
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willReturn(EMPTY_PASSWORD_HISTORY_ENTITY);
        willThrow(DataIntegrityViolationException.class).given(this.passwordHistoryRepository).delete(EMPTY_PASSWORD_HISTORY_ENTITY);

        // When
        this.passwordChangeService.deleteOldestPassword(USER_ID);

        // Then
    }

    @Test
    public void deleteOldestPasswordShouldDeleteHistoryEntity() {
        // Given
        given(this.passwordHistoryRepository.findOldestPassword(USER_ID)).willReturn(EMPTY_PASSWORD_HISTORY_ENTITY);

        // When
        this.passwordChangeService.deleteOldestPassword(USER_ID);

        // Then
        then(this.passwordHistoryRepository).should().findOldestPassword(USER_ID);
        then(this.passwordHistoryRepository).should().delete(EMPTY_PASSWORD_HISTORY_ENTITY);
        verifyNoMoreInteractions(this.passwordHistoryRepository);
        verifyZeroInteractions(this.userRepository);
    }

    private List<PasswordHistoryEntity> getPasswordHistoryEntitylistWithElements(int numberOfElements) {
        List<PasswordHistoryEntity> r = new ArrayList<>();
        for (int i = 0; i < numberOfElements; ++i) {
            r.add(PasswordHistoryEntity.builder()
                    .password(PASSWORD_PREFIX + i)
                    .build());
        }
        return r;
    }

}
