package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;

import java.util.List;

public interface PasswordChangeService {

    Long saveOrUpdate(PasswordHistoryDTO passwordHistoryDTO);

    List<String> isUnique(Long id);
}
