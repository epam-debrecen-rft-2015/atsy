package com.epam.rft.atsy.persistence.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "candidates", schema = "atsy")
public class CandidateEntity extends SuperEntity implements Serializable {


  @Column(name = "name", length = 255)
  private String name;

  @Column(name = "email", length = 400)
  private String email;

  @Column(name = "phone", length = 20)
  private String phone;

  @Lob
  @Column(name = "description")
  private String description;

  @Column(name = "referer", length = 255)
  private String referer;

  @Column(name = "language_skill")
  private Short languageSkill;


  @Builder
  public CandidateEntity(Long id, String name, String email, String phone, String description,
                         String referer, Short languageSkill) {
    super(id);
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.description = description;
    this.referer = referer;
    this.languageSkill = languageSkill;
  }
}
