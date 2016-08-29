package com.epam.rft.atsy.helper;


import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileCreatorTestHelper {

  public static final String MULTIPART_FILE_NAME = "file";
  public static final String CONTENT_TYPE = "text/plain";

  public static MockMultipartFile createMultipartFile(String originalFilename, Long fileSize) {
    return new MockMultipartFile(MULTIPART_FILE_NAME, originalFilename, CONTENT_TYPE,
        getStringBySize(fileSize).getBytes());
  }

  protected static String getStringBySize(Long length) {
    char singleCharacter = 'a';
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < length; i++) {
      stringBuffer.append(singleCharacter);
    }
    return stringBuffer.toString();
  }
}
