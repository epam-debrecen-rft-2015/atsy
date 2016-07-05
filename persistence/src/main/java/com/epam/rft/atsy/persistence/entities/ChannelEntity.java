package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ChannelEntity extends SuperEntity implements java.io.Serializable {


    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

}
