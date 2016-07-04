package com.epam.rft.atsy.service.request;

public class FilterRequest extends SortingRequest {


    private SearchOptions searchOptions;

    public SearchOptions getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilterRequest that = (FilterRequest) o;

        return !(searchOptions != null ? !searchOptions.equals(that.searchOptions) : that.searchOptions != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (searchOptions != null ? searchOptions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterRequest{" +
                "searchOptions=" + searchOptions +
                '}';
    }
}
