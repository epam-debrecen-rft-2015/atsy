package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ChannelEntity extends SuperEntity implements java.io.Serializable {


    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;


    public ChannelEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

}
