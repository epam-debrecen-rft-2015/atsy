package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.GenericDAO;
import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by mates on 10/22/2015.
 */

public class GenericDAOImpl<T, PK extends Serializable>
        implements GenericDAO<T, PK> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    public T update(T t) {
        return this.entityManager.merge(t);
    }

    public Collection<T> loadAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    private CriteriaQuery<T> order(SortingRequest.Order order,String fieldName){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        if(SortingRequest.Order.ASC == order) {
            cq.orderBy(cb.asc(rootEntry.get(fieldName)));
        } else if (SortingRequest.Order.DESC == order) {
            cq.orderBy(cb.desc(rootEntry.get(fieldName)));
        }
        return cq;
    }

    public Collection<T> loadAll(SortingRequest sortingRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        //CriteriaQuery cq=order(sortingRequest.getOrder(),sortingRequest.getFieldName());
        Root<T> rootEntry = cq.from(entityClass);
        if(SortingRequest.Order.ASC == sortingRequest.getOrder()) {
            cq.orderBy(cb.asc(rootEntry.get(sortingRequest.getFieldName())));
        } else if (SortingRequest.Order.DESC == sortingRequest.getOrder()) {
            cq.orderBy(cb.desc(rootEntry.get(sortingRequest.getFieldName())));
        }
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }



    public Collection<T> loadAll(FilterRequest filterRequest) {
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();

        CriteriaQuery cq = order(filterRequest.getOrder(),filterRequest.getFieldName());
        Root<T> rootEntry = cq.from(entityClass);

        Map<String,String> filters=filterRequest.getFilters();

        List<Predicate> predicates= new ArrayList<>();

        for(String fieldName:filters.keySet()){
            Predicate predicate= cb.and(cb.like(rootEntry.get(fieldName), filters.get(fieldName)));
            predicates.add(predicate);
        }
        CriteriaQuery<T> filter = cq.where(cb.and(predicates.toArray(new Predicate[]{})));
        TypedQuery<T> filterQuery = entityManager.createQuery(filter);
        return filterQuery.getResultList();

    }

    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }
}
