package com.cg.service.esport.securitycode;

import com.cg.domain.esport.entities.SecurityCode;
import com.cg.domain.esport.entities.User;
import com.cg.repository.esport.CodeSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SecurityCodeServiceImp implements ISecurityCodeService{
    @Autowired
    private CodeSecurityRepository codeSecurityRepository;
    @Override
    public List<SecurityCode> findAll() {
        return codeSecurityRepository.findAll();
    }


    @Override
    public SecurityCode getById(String id) {
        return codeSecurityRepository.getById(id);
    }

    @Override
    public Optional<SecurityCode> findById(String id) {
        return codeSecurityRepository.findById(id);
    }

    @Override
    public SecurityCode save(SecurityCode securityCode) {
        return codeSecurityRepository.save(securityCode);
    }
    @Override
    public void remove(String id) {
        codeSecurityRepository.deleteById(id);
    }

    @Override
    public SecurityCode findByUser(User user) {
        return codeSecurityRepository.getByUser(user);
    }

    @Override
    public SecurityCode getById(Long id) {
        return null;
    }

    @Override
    public Optional<SecurityCode> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {

    }


}
