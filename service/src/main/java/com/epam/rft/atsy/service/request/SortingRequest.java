package com.epam.rft.atsy.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
