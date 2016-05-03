package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public SuperEntity() {
    }

    public SuperEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
