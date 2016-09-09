package com.epam.rft.atsy.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse {

  private Long total;
  private List<?> dataList;

}
