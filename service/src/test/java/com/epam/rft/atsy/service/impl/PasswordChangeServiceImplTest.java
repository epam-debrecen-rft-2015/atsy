package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.PasswordHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordHistoryRepository passwordHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordChangeServiceImpl sut;

    @Test(expected = IllegalArgumentException.class)
    public void getOldPasswordShouldThrowIAEWhenIdIsNull() {
        // Given

        // When
        this.sut.getOldPasswords(null);

        // Then
    }

    @Test(expected = DataAccessException.class)
    public void getOldPasswordShouldDelegateDataAccessExceptionFromUserRepository() {
        // Given
        final Long ID = 1L;
        given(this.userRepository.findOne(ID)).willThrow(DataIntegrityViolationException.class);

        // When
        this.sut.getOldPasswords(ID);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOldPasswordShouldThrowIAEWhenUserCannotBeFoundById() {
        // Given
        final Long ID = 1L;
        final UserEntity USER = null;
        given(this.userRepository.findOne(ID)).willReturn(USER);
        given(this.passwordHistoryRepository.findByUserEntity(USER)).willThrow(IllegalArgumentException.class);

        // When
        this.sut.getOldPasswords(ID);

        // Then
    }

    @Test
    public void getOldPasswordShouldReturnEmptyListWhenNoPasswordHistoryHasBeenFound() {
        // Given
        final Long ID = 1L;
        final UserEntity USER = UserEntity.builder().id(ID).build();
        final List<PasswordHistoryEntity> HISTORY = Collections.emptyList();
        given(this.userRepository.findOne(ID)).willReturn(USER);
        given(this.passwordHistoryRepository.findByUserEntity(USER)).willReturn(HISTORY);

        // When
        List<String> result = this.sut.getOldPasswords(ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.userRepository).should().findOne(ID);
        then(this.passwordHistoryRepository).should().findByUserEntity(USER);
    }

    @Test
    public void getOldPasswordShouldExtractPasswordFromHistoryWhenThereIsPasswordHistory() {
        // Given
        final Long ID = 1L;
        final UserEntity USER = UserEntity.builder().id(ID).build();

        final String PASSWORD1 = "pass1";
        final String PASSWORD2 = "password2";
        final List<PasswordHistoryEntity> HISTORY = Arrays.asList(
                PasswordHistoryEntity.builder().password(PASSWORD1).build(),
                PasswordHistoryEntity.builder().password(PASSWORD2).build()
        );
        final List<String> EXPECTED = Arrays.asList(PASSWORD1, PASSWORD2);

        given(this.userRepository.findOne(ID)).willReturn(USER);
        given(this.passwordHistoryRepository.findByUserEntity(USER)).willReturn(HISTORY);

        // When
        List<String> result = this.sut.getOldPasswords(ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(false));
        assertThat(result, equalTo(EXPECTED));

        then(this.userRepository).should().findOne(ID);
        then(this.passwordHistoryRepository).should().findByUserEntity(USER);
    }

}
