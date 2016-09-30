package com.epam.rft.atsy.web.validator;


import com.epam.rft.atsy.helper.MultipartFileCreatorTestHelper;
import com.epam.rft.atsy.service.exception.file.FileContainsInvalidCharacterValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsMissingValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

public class FileValidatorImplTest {

  private static final String ORIGINAL_FILENAME_IS_EMPTY = StringUtils.EMPTY;
  private static final String ORIGINAL_FILENAME_CONTAINS_INVALID_CHARACTER = "cat$.pdf";
  private static final String ORIGINAL_FILENAME_IN_WRONG_EXTENSION = "cat.txt";

  private static final String ORIGINAL_FILENAME_IS_VALID_DOC = "CAAT_ _0.doc";
  private static final String ORIGINAL_FILENAME_IS_VALID_DOCX = "caaat_ _0123456789.docx";
  private static final String ORIGINAL_FILENAME_IS_VALID_ODT = "CaaaT__0-9.odt";
  private static final String ORIGINAL_FILENAME_IS_VALID_PDF = "cAAt.pdf";

  private static final int FILE_SIZE_ZERO_MB = 0;
  private static final int FILE_SIZE_FIVE_MB = 1024 * 1024 * 5;
  private static final int FILE_SIZE_TEN_MB_MINUS_ONE_BYTE = (1024 * 1024 * 10) - 1;
  private static final int FILE_SIZE_TEN_MB = (1024 * 1024 * 10);
  private static final int FILE_SIZE_TEN_MB_PLUS_ONE_BYTE = (1024 * 1024 * 10) + 1;

  private FileValidator fileValidator = new FileValidatorImpl();

  @Test(expected = IllegalArgumentException.class)
  public void validateShouldThrowIllegalArgumentExceptionWhenFileIsNull()
      throws FileValidationException {

    fileValidator.validate(null);
  }

  @Test(expected = FileIsMissingValidationException.class)
  public void isMissingFileShouldThrowFileIsMissingValidationExceptionWhenOriginalFilenameIsEmpty()
      throws FileValidationException {

    MultipartFile multipartFile = MultipartFileCreatorTestHelper
        .createMultipartFile(ORIGINAL_FILENAME_IS_EMPTY, FILE_SIZE_ZERO_MB);
    fileValidator.validate(multipartFile);
  }

  @Test(expected = FileIsEmptyValidationException.class)
  public void isEmptyFileShouldThrowFileIsEmptyValidationExceptionWhenFileIsEmpty()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_PDF, FILE_SIZE_ZERO_MB);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void isGreaterFileSizeThanTheMaximumFileSizeShouldNotThrowFileIsTooLargeValidationExceptionWhenFileSizeIsLessThanMaximumFileSize()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_PDF, FILE_SIZE_TEN_MB_MINUS_ONE_BYTE);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void isGreaterFileSizeThanTheMaximumFileSizeShouldNotThrowFileIsTooLargeValidationExceptionWhenFileSizeEqualsMaximumFileSize()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_PDF, FILE_SIZE_TEN_MB);
    fileValidator.validate(multipartFile);
  }

  @Test(expected = FileIsTooLargeValidationException.class)
  public void isGreaterFileSizeThanTheMaximumFileSizeShouldThrowFileIsTooLargeValidationExceptionWhenFileSizeIsGreaterThanMaximumFileSize()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_PDF, FILE_SIZE_TEN_MB_PLUS_ONE_BYTE);
    fileValidator.validate(multipartFile);
  }

  @Test(expected = FileIsInWrongExtensionValidationException.class)
  public void isFileInWrongExtensionShouldThrowFileIsInWrongExtensionValidationExceptionWhenFileIsInWrongExtension()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IN_WRONG_EXTENSION, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

  @Test(expected = FileContainsInvalidCharacterValidationException.class)
  public void isFileContainsNotOnlyValidCharactersShouldThrowFileContainsInvalidCharacterValidationExceptionWhenFileContainsInvalidCharacter()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_CONTAINS_INVALID_CHARACTER, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void validateShouldNotThrowFileValidationExceptionWhenOriginalFileNameIsValidDoc()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_DOC, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void validateShouldNotThrowFileValidationExceptionWhenOriginalFileNameIsValidDocx()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_DOCX, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void validateShouldNotThrowFileValidationExceptionWhenOriginalFileNameIsValidOdt()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_ODT, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

  @Test
  public void validateShouldNotThrowFileValidationExceptionWhenOriginalFileNameIsValidPdf()
      throws FileValidationException {

    MultipartFile multipartFile =
        MultipartFileCreatorTestHelper
            .createMultipartFile(ORIGINAL_FILENAME_IS_VALID_PDF, FILE_SIZE_FIVE_MB);
    fileValidator.validate(multipartFile);
  }

}
