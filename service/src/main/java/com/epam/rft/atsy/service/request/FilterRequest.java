package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represent a filter request. This class extends the {@code SortingRequest} class and stores {@code
 * SearchOptions} object. See the corresponding documentations: {@see
 * com.epam.rft.atsy.service.request.SortingRequest}, {@see com.epam.rft.atsy.service.request.SearchOptions}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest extends SortingRequest {
  private SearchOptions searchOptions;

  /**
   * Creates a new instance of {@code FilterRequest}.
   * @param fieldName a {@code Field} which represent the name of the field which is to be sorted
   * @param order an {@code Order} which represent the order of the sorting
   * @param searchOptions a {@SearchOptions} which represents a search
   */
  @Builder
  public FilterRequest(Field fieldName, Order order, SearchOptions searchOptions) {
    super(fieldName, order);
    this.searchOptions = searchOptions;
  }

}
