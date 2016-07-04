package com.epam.rft.atsy.persistence.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest extends SortingRequest {


    private SearchOptions searchOptions;

}
