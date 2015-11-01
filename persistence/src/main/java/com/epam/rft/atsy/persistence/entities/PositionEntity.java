package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by mates on 10/22/2015.
 */
@Entity
@Table(name = "Positions", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class PositionEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "positionId")
    private long positionId;
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

    /**
     * Empty constructor.
     */
    public PositionEntity() {
    }

    /**
     * Full constructor.
     *
     * @param positionId is the ID of the position
     * @param name       is the name of the position
     */
    public PositionEntity(long positionId, String name) {
        this.positionId = positionId;
        this.name = name;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
