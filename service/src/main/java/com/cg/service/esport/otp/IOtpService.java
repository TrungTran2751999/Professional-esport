package com.cg.service.esport.otp;

import com.cg.domain.esport.entities.Otp;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IOtpService extends IGeneralService<Otp> {
    Otp getById(String id);
    Optional<Otp> findById(String id);
    void remove(String id);
}
