package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by szabo on 2015. 11. 07..
 */
@Entity
@Table(name = "Candidates", schema = "atsy")
public class CandidateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidateId")
    private Long candidateId;
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

    public CandidateEntity() {
    }

    public CandidateEntity(String name, String email, String phone, String description, String referer, Short languageSkill) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.referer = referer;
        this.languageSkill = languageSkill;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Short getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(Short language_skill) {
        this.languageSkill = language_skill;
    }
}
