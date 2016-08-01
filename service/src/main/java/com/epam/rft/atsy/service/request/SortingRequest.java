package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    NAME {
      @Override
      public String toString() {
        return this.name().toLowerCase();
      }
    },
    EMAIL {
      @Override
      public String toString() {
        return this.name().toLowerCase();
      }
    },
    PHONE {
      @Override
      public String toString() {
        return this.name().toLowerCase();
      }
    }
  }

}
