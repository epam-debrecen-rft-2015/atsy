package com.epam.rft.atsy.service.domain;

import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Data transfer class that is used to transfer personal data of a candidate between the service and
 * web layers. Contains validation rules. See {@link com.epam.rft.atsy.persistence.entities.CandidateEntity
 * CandidateEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CandidateDTO extends LogicallyDeletableDTO {

  @NotNull
  @Size(min = 1, max = 100, message = "candidate.error.name.long")
  private String name;

  @NotNull
  @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}", message = "candidate.error.email.incorrect")
  @Size(max = 255, message = "candidate.error.email.long")
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

  private Set<String> positions;
  private String cvFilename;

  @Builder
  public CandidateDTO(Long id, Boolean deleted, String name, String email, String phone,
                      String referer, Short languageSkill, String description,
                      Set<String> positions,
                      String cvFilename) {
    super(id, deleted);
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.referer = referer;
    this.languageSkill = languageSkill;
    this.description = description;
    this.positions = positions;
    this.cvFilename = cvFilename;
  }
}