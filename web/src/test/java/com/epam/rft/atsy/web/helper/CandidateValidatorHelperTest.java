package com.epam.rft.atsy.web.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CandidateValidatorHelperTest {

  private static final String CANDIDATE_A_EMAIL = "candidateA.email@email.com";
  private static final String CANDIDATE_A_PHONE = "+3630555555";
  private static final String CANDIDATE_NEW_EMAIL = "candidateNew.email@email.com";
  private static final String CANDIDATE_NEW_PHONE = "+367055555";

  private static final long CANDIDATE_A_ID = 1;
  private static final long CANDIDATE_NEW_ID = 2;

  @Mock
  private CandidateService candidateService;

  @InjectMocks
  private CandidateValidatorHelper candidateValidatorHelper;

  @Test(expected = IllegalArgumentException.class)
  public void isValidNonExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidateIsNull() {
    // Given

    // When
    this.candidateValidatorHelper.isValidNonExistingCandidate(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidNonExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidateEmailIsNull() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().email(null).phone(CANDIDATE_NEW_PHONE).build();

    // When
    this.candidateValidatorHelper.isValidNonExistingCandidate(actualCandidateDto);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidNonExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidatePhoneIsNull() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().email(CANDIDATE_NEW_EMAIL).phone(null).build();

    // When
    this.candidateValidatorHelper.isValidNonExistingCandidate(actualCandidateDto);

    // Then
  }

  @Test
  public void isValidNonExistingCandidateShouldReturnFalseWhenEmailAlreadyExists() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().email(CANDIDATE_A_EMAIL).phone(CANDIDATE_NEW_PHONE).build();
    final CandidateDTO
        candidateDtoWithEmail =
        CandidateDTO.builder().email(CANDIDATE_A_EMAIL).phone(CANDIDATE_A_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_A_EMAIL))
        .willReturn(candidateDtoWithEmail);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE)).willReturn(null);

    // When
    boolean result = this.candidateValidatorHelper.isValidNonExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(false));

    then(this.candidateService.getCandidateDtoByEmail(CANDIDATE_A_EMAIL));
    then(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE));
  }

  @Test
  public void isValidNonExistingCandidateShouldReturnFalseWhenPhoneAlreadyExists() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().email(CANDIDATE_NEW_EMAIL).phone(CANDIDATE_A_PHONE).build();
    final CandidateDTO
        candidateDtoWithPhone =
        CandidateDTO.builder().email(CANDIDATE_A_EMAIL).phone(CANDIDATE_A_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL)).willReturn(null);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_A_PHONE))
        .willReturn(candidateDtoWithPhone);

    // When
    boolean result = this.candidateValidatorHelper.isValidNonExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(false));

    then(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL));
    then(this.candidateService.getCandidateDtoByPhone(CANDIDATE_A_PHONE));
  }

  @Test
  public void isValidNonExistingCandidateShouldReturnTrueWhenEmailAndPhoneNotExist() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().email(CANDIDATE_NEW_EMAIL).phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL)).willReturn(null);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE)).willReturn(null);

    // When
    boolean result = this.candidateValidatorHelper.isValidNonExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(true));

    then(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL));
    then(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE));
  }


  @Test(expected = IllegalArgumentException.class)
  public void isValidExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidateIsNull() {
    // Given

    // When
    this.candidateValidatorHelper.isValidExistingCandidate(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidateEmailIsNull() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(null).phone(CANDIDATE_NEW_PHONE).build();

    // When
    this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidatePhoneIsNull() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL).phone(null).build();

    // When
    this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidExistingCandidateShouldThrowIllegalArgumentExceptionWhenCandidateIdIsNull() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(null).email(CANDIDATE_NEW_EMAIL).phone(CANDIDATE_NEW_PHONE).build();

    // When
    this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
  }

  @Test
  public void isValidExistingCandidateShouldReturnFalseWhenCandidateEmailAlreadyExists() {
    // Given
    final CandidateDTO
        candidateDtoWithEmail =
        CandidateDTO.builder().id(CANDIDATE_A_ID).email(CANDIDATE_A_EMAIL).phone(CANDIDATE_A_PHONE)
            .build();
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_A_EMAIL)
            .phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_A_EMAIL)).willReturn(candidateDtoWithEmail);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE)).willReturn(null);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(false));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_A_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_NEW_PHONE);
  }

  @Test
  public void isValidExistingCandidateShouldReturnFalseWhenCandidatePhoneAlreadyExists() {
    // Given
    final CandidateDTO
        candidateDtoWithPhone =
        CandidateDTO.builder().id(CANDIDATE_A_ID).email(CANDIDATE_A_EMAIL).phone(CANDIDATE_A_PHONE)
            .build();
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL)
            .phone(CANDIDATE_A_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL)).willReturn(null);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_A_PHONE)).willReturn(candidateDtoWithPhone);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(false));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_A_PHONE);
  }

  @Test
  public void isValidExistingCandidateShouldReturnTrueWhenEmailAndPhoneNotExist() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL)
            .phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL)).willReturn(null);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE)).willReturn(null);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(true));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_NEW_PHONE);
  }

  @Test
  public void isValidExistingCandidateShouldReturnTrueWhenEmailAndPhoneNotBeenUpdated() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL)
            .phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL))
        .willReturn(actualCandidateDto);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE))
        .willReturn(actualCandidateDto);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(true));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_NEW_PHONE);
  }

  @Test
  public void isValidExistingCandidateShouldReturnTrueWhenEmailNotExistsAndPhoneNotBeenUpdated() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL)
            .phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL))
        .willReturn(null);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE))
        .willReturn(actualCandidateDto);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(true));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_NEW_PHONE);
  }

  @Test
  public void isValidExistingCandidateShouldReturnTrueWhenPhoneNotExistsAndEmailNotBeenUpdated() {
    // Given
    final CandidateDTO
        actualCandidateDto =
        CandidateDTO.builder().id(CANDIDATE_NEW_ID).email(CANDIDATE_NEW_EMAIL)
            .phone(CANDIDATE_NEW_PHONE).build();

    given(this.candidateService.getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL))
        .willReturn(actualCandidateDto);
    given(this.candidateService.getCandidateDtoByPhone(CANDIDATE_NEW_PHONE))
        .willReturn(null);

    // When
    boolean result = this.candidateValidatorHelper.isValidExistingCandidate(actualCandidateDto);

    // Then
    assertThat(result, is(true));

    then(this.candidateService).should().getCandidateDtoByEmail(CANDIDATE_NEW_EMAIL);
    then(this.candidateService).should().getCandidateDtoByPhone(CANDIDATE_NEW_PHONE);
  }
}
