package com.epam.rft.atsy.persistence.request;

/**
 * Created by tothd on 2015. 11. 07..
 */
public class CandidateRequestDTO {

    private String fieldName;

    public enum Order {
        ASC,
        DESC
    }

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
