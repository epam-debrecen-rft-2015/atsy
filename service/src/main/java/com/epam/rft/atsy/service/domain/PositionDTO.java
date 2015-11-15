package com.epam.rft.atsy.service.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Ikantik.
 */
public class PositionDTO implements Serializable {
    private Long positionId;
    @NotNull
    @Size(min = 1)
    private String name;

    public PositionDTO(Long positionId, String name) {
        this.positionId = positionId;
        this.name = name;
    }

    public PositionDTO() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PositionDTO that = (PositionDTO) o;

        return new EqualsBuilder()
                .append(positionId, that.positionId)
                .append(name, that.name)
                .isEquals();
    }
}
