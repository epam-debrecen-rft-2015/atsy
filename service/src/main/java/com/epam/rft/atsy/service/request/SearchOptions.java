package com.epam.rft.atsy.service.request;

import com.google.common.base.MoreObjects;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represent a search. It stores the values of the searchable fields
 */
@Builder
@Data
@NoArgsConstructor
public class SearchOptions {

  private String name;
  private String email;
  private String phone;
  private String position;

  /**
   * Constructs a new instance of {@code SearchOptions}.
   * @param name a String which represents the value of the name field which will be searched in the
   * database
   * @param email a String which represents the value of the email field which will be searched in
   * the database
   * @param phone a String which represents the value of the phone field which will be searched in
   * the database
   * @param positions a String which represents the value of the positions field which will be
   * searched in the database
   */
  public SearchOptions(String name, String email, String phone, String position) {
    this.name = MoreObjects.firstNonNull(name, StringUtils.EMPTY);
    this.email = MoreObjects.firstNonNull(email, StringUtils.EMPTY);
    this.phone = MoreObjects.firstNonNull(phone, StringUtils.EMPTY);
    this.position = MoreObjects.firstNonNull(position, StringUtils.EMPTY);
  }
}
