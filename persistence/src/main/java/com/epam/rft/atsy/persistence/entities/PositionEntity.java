package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "Positions", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class PositionEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long positionId;
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
