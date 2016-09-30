package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingAndPagingRequest {

  private String sortName;
  private String sortOrder;
  private Integer pageSize;
  private Integer pageNumber;

}
