package com.epam.rft.atsy.persistence.entities.states;

import com.epam.rft.atsy.persistence.entities.PositionEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tothd on 2015. 12. 01..
 */
@Entity
@Table(name = "States", schema = "atsy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "STATE_TYPE")
public abstract class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stateId")
    private Long stateId;
    @Column(name = "candidateId")
    private Long candidateId;
    @Column(name = "position")
    private PositionEntity positionId;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "description")
    private String description;
    @OneToOne
    private StateEntity nextState;
}

