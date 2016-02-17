package com.epam.rft.atsy.persistence.request;

import java.util.Map;

public class FilterRequest extends SortingRequest {

    private Map<String,String> filters;

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilterRequest that = (FilterRequest) o;

        return !(filters != null ? !filters.equals(that.filters) : that.filters != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        return result;
    }
}
