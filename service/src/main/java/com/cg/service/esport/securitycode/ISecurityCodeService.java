package com.cg.service.esport.securitycode;

import com.cg.domain.esport.entities.SecurityCode;
import com.cg.domain.esport.entities.User;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface ISecurityCodeService extends IGeneralService<SecurityCode> {
    SecurityCode getById(String id);
    Optional<SecurityCode> findById(String id);
    void remove(String id);
    SecurityCode findByUser(User user);
}
