package com.epam.rft.atsy.persistence.request;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingRequest {


    public enum Order {
        ASC,
        DESC
    }

    private String fieldName;
    private Order order;

}
