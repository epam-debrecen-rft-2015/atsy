package com.epam.rft.atsy.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the password history of each user, in the database.
 * (Which user changed his/her password to what and when.)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "password")
@Entity
@Table(name = "password_history", schema = "atsy")
public class PasswordHistoryEntity extends SuperEntity implements Serializable {


  @ManyToOne(targetEntity = UserEntity.class)
  @JoinColumn(name = "users_id", nullable = false)
  private UserEntity userEntity;

  @Column(name = "password", table = "password_history", nullable = false)
  private String password;

  @Column(name = "change_date", table = "password_history", length = 255, nullable = false)
  private Date changeDate;


  @Builder
  public PasswordHistoryEntity(Long id, UserEntity userEntity, String password, Date changeDate) {
    super(id);
    this.userEntity = userEntity;
    this.password = password;
    this.changeDate = changeDate;
  }

}
