package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor public class FilterRequest extends SortingRequest {


    private SearchOptions searchOptions;


    @Builder public FilterRequest(String fieldName, Order order, SearchOptions searchOptions) {
        super(fieldName, order);
        this.searchOptions = searchOptions;
    }

}
