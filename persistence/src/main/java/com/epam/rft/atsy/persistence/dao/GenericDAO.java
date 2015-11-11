package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by mates on 10/22/2015.
 */
public interface GenericDAO<T, PK extends Serializable> {
    T create(T t);

    T read(PK id);

    Collection<T> loadAll();
    Collection<T> loadAll(CandidateRequestDTO dto);

    T update(T t);

    void delete(T t);
}