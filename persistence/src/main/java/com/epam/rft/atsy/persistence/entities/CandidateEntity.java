package com.epam.rft.atsy.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@NoArgsConstructor
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


    @Builder
    public CandidateEntity(Long id, String name, String email, String phone, String description, String referer, Short languageSkill) {
        super(id);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.referer = referer;
        this.languageSkill = languageSkill;
    }
}
