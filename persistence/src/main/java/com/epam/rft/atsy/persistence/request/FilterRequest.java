package com.epam.rft.atsy.persistence.request;

import java.util.Map;

public class FilterRequest extends SortingRequest {


    private SearchOptions searchOptions;

    public SearchOptions getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
    }
}
