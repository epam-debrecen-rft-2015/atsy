package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;

public interface PasswordChangeService {

    Long saveOrUpdate(PasswordHistoryDTO passwordHistoryDTO);
}
