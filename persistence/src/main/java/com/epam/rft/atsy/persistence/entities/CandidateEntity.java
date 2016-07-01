package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

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

    public CandidateEntity() {
    }

    public CandidateEntity(Long id, String name, String email, String phone, String description, String referer, Short languageSkill) {
        super(id);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.referer = referer;
        this.languageSkill = languageSkill;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CandidateEntity that = (CandidateEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (referer != null ? !referer.equals(that.referer) : that.referer != null) return false;
        return !(languageSkill != null ? !languageSkill.equals(that.languageSkill) : that.languageSkill != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (referer != null ? referer.hashCode() : 0);
        result = 31 * result + (languageSkill != null ? languageSkill.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CandidateEntity{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", referer='" + referer + '\'' +
                ", languageSkill=" + languageSkill +
                '}';
    }
}
