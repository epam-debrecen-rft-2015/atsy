package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data @NoArgsConstructor @AllArgsConstructor @MappedSuperclass public abstract class SuperEntity {


    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id") private Long id;

}
