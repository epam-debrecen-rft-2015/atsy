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
@DiscriminatorColumn(name = "state_type")
public abstract class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stateId")
    private Long stateId;
    @Column(name = "candidateId")
    private Long candidateId;
    @ManyToOne
    @JoinColumn(name = "positionId", nullable = true)
    private PositionEntity positionId;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "next_state", nullable = true)
    private StateEntity nextState;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public PositionEntity getPositionId() {
        return positionId;
    }

    public void setPositionId(PositionEntity positionId) {
        this.positionId = positionId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateEntity getNextState() {
        return nextState;
    }

    public void setNextState(StateEntity nextState) {
        this.nextState = nextState;
    }
}

