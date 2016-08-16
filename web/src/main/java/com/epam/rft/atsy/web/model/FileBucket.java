package com.epam.rft.atsy.web.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileBucket {

  private MultipartFile file;
}
