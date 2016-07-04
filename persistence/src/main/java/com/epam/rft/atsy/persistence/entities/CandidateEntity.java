package com.epam.rft.atsy.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
