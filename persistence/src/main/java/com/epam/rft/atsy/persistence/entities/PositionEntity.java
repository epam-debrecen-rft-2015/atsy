package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "positions", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class PositionEntity extends SuperEntity implements java.io.Serializable {


    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;


    public PositionEntity(Long id, String name) {
        super(id);
        this.name = name;
    }
}
