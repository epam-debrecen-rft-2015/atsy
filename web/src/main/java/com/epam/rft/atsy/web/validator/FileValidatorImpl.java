package com.epam.rft.atsy.web.validator;


import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileValidatorImpl implements FileValidator {

  private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB
  private static final String FILE_PATTERN = "([^\\s]+(\\.(?i)(doc|docx|odt|pdf))$)";

  private Pattern pattern;
  private Matcher matcher;

  @Override
  public void validate(MultipartFile file) throws FileValidationException {
    Assert.notNull(file);

    if (isEmptyFile(file)) {
      throw new FileIsEmptyValidationException();
    }
    if (isGreaterFileSizeThanTheMaximumFileSize(file)) {
      throw new FileIsTooLargeValidationException();
    }
    /*if (isUnCorrectFileExtension(file)) {
      throw new FileIsInWrongExtensionValidationException();
    }*/
  }


  protected boolean isEmptyFile(MultipartFile file) {
    return file.isEmpty();
  }

  protected boolean isGreaterFileSizeThanTheMaximumFileSize(MultipartFile file) {
    return file.getSize() > MAX_FILE_SIZE;
  }

  protected boolean isUnCorrectFileExtension(MultipartFile file) {
    return !isCorrectFileExtension(file);
  }

  protected boolean isCorrectFileExtension(MultipartFile file) {
    pattern = Pattern.compile(FILE_PATTERN);
    matcher = pattern.matcher(file.getOriginalFilename());
    System.out.println(file.getOriginalFilename());
    return matcher.matches();
  }
}