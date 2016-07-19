package com.epam.rft.atsy.service.request;

import com.google.common.base.MoreObjects;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
public class SearchOptions {


  private String name;
  private String email;
  private String phone;


  public SearchOptions(String name, String email, String phone) {
    this.name = MoreObjects.firstNonNull(name, StringUtils.EMPTY);
    this.email = MoreObjects.firstNonNull(email, StringUtils.EMPTY);
    this.phone = MoreObjects.firstNonNull(phone, StringUtils.EMPTY);
  }
}
