package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "candidates", schema = "atsy")
public class CandidateEntity extends SuperEntity implements Serializable {


    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "referer", length = 255)
    private String referer;

    @Column(name = "language_skill")
    private Short languageSkill;

}
