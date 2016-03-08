package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity,Long> {
}
