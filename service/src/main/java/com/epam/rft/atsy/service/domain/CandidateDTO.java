package com.epam.rft.atsy.service.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tothd on 2015. 11. 07..
 */
public class CandidateDTO {
    private Long candidateId;
    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    private String email;


    @NotNull
    @Size(min = 1)
    private String phone;

    private String referer;

    private Byte languageSkill;

    private String description;

    public CandidateDTO() {
    }

    public CandidateDTO(String name, String email, String phone, String description, String referer, Byte languageSkill) {
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

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public int getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(Byte languageSkill) {
        this.languageSkill = languageSkill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateDTO that = (CandidateDTO) o;

        if (candidateId != null ? !candidateId.equals(that.candidateId) : that.candidateId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (referer != null ? !referer.equals(that.referer) : that.referer != null) return false;
        if (languageSkill != null ? !languageSkill.equals(that.languageSkill) : that.languageSkill != null)
            return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = candidateId != null ? candidateId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (referer != null ? referer.hashCode() : 0);
        result = 31 * result + (languageSkill != null ? languageSkill.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
