package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.LogicallyDeletableEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Repository that allows operations with logically deletable entities in database.
 *
 * @param <T>  the type of the entity
 * @param <ID> the id of the entity
 */
@NoRepositoryBean
public interface LogicallyDeletableRepository<T extends LogicallyDeletableEntity, ID extends Serializable>
    extends JpaRepository<T, ID> {

  /**
   * Returns the list of T where the deleted field is false.
   *
   * @return the list of T where the deleted field is false
   */
  List<T> findAllByDeletedFalse();
}
