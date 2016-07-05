package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "states", schema = "atsy")
public class StateEntity extends SuperEntity {


    @OneToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity applicationEntity;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "language_skill")
    private Short languageSkill;

    @Column(name = "description")
    private String description;

    @Column(name = "first_test_result")
    private String result;

    @Column(name = "offered_money")
    private Long offeredMoney;

    @Column(name = "claim")
    private Long claim;

    @Column(name = "feedback_date")
    private Date feedbackDate;

    @Column(name = "state_type", updatable = false)
    private String stateType;

    @Column(name = "state_index")
    private Integer stateIndex;

}

