package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications", schema = "atsy")
public class ApplicationEntity extends SuperEntity {


    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidateEntity;

    @OneToOne
    @JoinColumn(name = "position_id")
    private PositionEntity positionEntity;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channelEntity;


    public ApplicationEntity(Long id, Date creationDate, CandidateEntity candidateEntity, PositionEntity positionEntity, ChannelEntity channelEntity) {
        super(id);
        this.creationDate = creationDate;
        this.candidateEntity = candidateEntity;
        this.positionEntity = positionEntity;
        this.channelEntity = channelEntity;
    }

}
