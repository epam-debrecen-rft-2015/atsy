package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a sorting request. It stores the name of the field which is to be sorted
 * and the order of the sorting.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingRequest {

  private Field fieldName;
  private Order order;

  public enum Order {
    ASC,
    DESC
  }

  public enum Field {
    NAME, EMAIL, PHONE, POSITIONS;

    @Override
    public String toString() {
      return this.name().toLowerCase();
    }
  }

}
