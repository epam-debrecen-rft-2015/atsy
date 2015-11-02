package com.epam.rft.atsy.service.domain;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Ikantik.
 */
public class PositionDTO implements Serializable {
    private Long positionId;
    private String name;

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
