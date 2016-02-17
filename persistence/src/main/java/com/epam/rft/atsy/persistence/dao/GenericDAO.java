package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;

import java.io.Serializable;
import java.util.Collection;

public interface GenericDAO<T, PK extends Serializable> {
    T create(T t);

    T read(PK id);

    Collection<T> loadAll();
    Collection<T> loadAll(SortingRequest sortingRequest);
    Collection<T> loadAll (FilterRequest filterRequest);

    T update(T t);

    void delete(T t);
}