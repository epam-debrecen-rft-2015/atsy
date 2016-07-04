package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {


    private Long id;

    @NotNull
    @Size(min = 1, max =100, message = "candidate.error.name.long")
    private String name;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}", message = "candidate.error.email.incorrect")
    @Size(min = 1, max = 400, message = "candidate.error.email.long")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]+", message = "candidate.error.phone.incorrect")
    @Size(max = 20, message = "candidate.error.phone.long")
    private String phone;

    @Size(max = 20, message = "candidate.error.referer.long")
    private String referer;

    @Min(0)
    @Max(10)
    private Short languageSkill;

    private String description;


    public CandidateDTO(String name, String email, String phone, String description, String referer, Short languageSkill) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.referer = referer;
        this.languageSkill = languageSkill;
    }

}
