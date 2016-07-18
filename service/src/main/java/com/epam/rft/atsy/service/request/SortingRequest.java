package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingRequest {


  private String fieldName;
  private Order order;
  public enum Order {
    ASC,
    DESC
  }

}
