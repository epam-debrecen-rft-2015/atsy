package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatesRepository extends JpaRepository<StatesEntity, Long> {
}
