package com.epam.rft.atsy.web.validator;


import com.epam.rft.atsy.service.exception.file.FileValidationException;
import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

  void validate(MultipartFile file) throws FileValidationException;
}
