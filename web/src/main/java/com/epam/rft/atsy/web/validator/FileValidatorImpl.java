package com.epam.rft.atsy.web.validator;


import com.epam.rft.atsy.service.exception.file.FileContainsInvalidCharacterValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsMissingValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileValidatorImpl implements FileValidator {

  private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB
  private static final String VALID_CHARACTERS_PATTERN = "([a-zA-Z0-9-_ ])+";
  private static final String EXTENSION_DOC = "doc";
  private static final String EXTENSION_DOCX = "docx";
  private static final String EXTENSION_ODT = "odt";
  private static final String EXTENSION_PDF = "pdf";
  private static final Collection<String> EXTENSIONS =
      Arrays.asList(EXTENSION_DOC, EXTENSION_DOCX, EXTENSION_ODT, EXTENSION_PDF);

  private Pattern pattern;
  private Matcher matcher;

  @Override
  public void validate(MultipartFile file) throws FileValidationException {
    Assert.notNull(file);

    if (isMissingFile(file)) {
      throw new FileIsMissingValidationException();
    }
    if (isEmptyFile(file)) {
      throw new FileIsEmptyValidationException();
    }
    if (isGreaterFileSizeThanTheMaximumFileSize(file)) {
      throw new FileIsTooLargeValidationException();
    }
    if (isFileInWrongExtension(file)) {
      throw new FileIsInWrongExtensionValidationException();
    }
    if (isFileContainsNotOnlyValidCharacters(file)) {
      throw new FileContainsInvalidCharacterValidationException();
    }
  }

  protected boolean isMissingFile(MultipartFile file) {
    return file.getOriginalFilename().isEmpty();
  }

  protected boolean isEmptyFile(MultipartFile file) {
    return file.isEmpty();
  }

  protected boolean isGreaterFileSizeThanTheMaximumFileSize(MultipartFile file) {
    return file.getSize() > MAX_FILE_SIZE;
  }

  protected boolean isFileInWrongExtension(MultipartFile file) {
    return !isFileInCorrectExtension(file);
  }

  protected boolean isFileInCorrectExtension(MultipartFile file) {
    return FilenameUtils.isExtension(file.getOriginalFilename(), EXTENSIONS);
  }

  protected boolean isFileContainsNotOnlyValidCharacters(MultipartFile file) {
    return !isFileContainsOnlyValidCharacters(file);
  }

  protected boolean isFileContainsOnlyValidCharacters(MultipartFile file) {
    pattern = Pattern.compile(VALID_CHARACTERS_PATTERN);
    matcher = pattern.matcher(FilenameUtils.getBaseName(file.getOriginalFilename()));
    return matcher.matches();
  }
}