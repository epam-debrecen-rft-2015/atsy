package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatesRepository extends JpaRepository<StatesEntity, Long> {

  /**
   * Returns a state with the specified name.
   * @param name  the name of the requested state
   * @return  state with the specified name
   */
  public StatesEntity findByName(String name);

}
