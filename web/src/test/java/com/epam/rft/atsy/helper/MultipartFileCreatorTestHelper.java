package com.epam.rft.atsy.helper;


import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileCreatorTestHelper {

  public static final String MULTIPART_FILE_NAME = "file";
  public static final String CONTENT_TYPE = "text/plain";

  public static MockMultipartFile createMultipartFile(String originalFilename, int fileSize) {
    return new MockMultipartFile(MULTIPART_FILE_NAME, originalFilename, CONTENT_TYPE,
        getStringBySize(fileSize).getBytes());
  }

  protected static String getStringBySize(int size) {
    char singleCharacter = 'a';
    return StringUtils.repeat(singleCharacter, size);
  }
}
