package com.cg.service.esport.otp;

import com.cg.domain.esport.entities.Otp;
import com.cg.repository.esport.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OtpServiceImp implements IOtpService{
    @Autowired
    private OtpRepository otpRepository;
    @Override
    public List<Otp> findAll() {
        return otpRepository.findAll();
    }

    @Override
    public Otp getById(String id) {
        return otpRepository.getById(id);
    }

    @Override
    public Optional<Otp> findById(String id) {
        return otpRepository.findById(id);
    }

    @Override
    public Otp save(Otp otp) {
        return otpRepository.save(otp);
    }

    @Override
    public void remove(String id) {
        otpRepository.deleteById(id);
    }

    @Override
    public void remove(Long id) {

    }
    @Override
    public Otp getById(Long id) {
        return null;
    }

    @Override
    public Optional<Otp> findById(Long id) {
        return Optional.empty();
    }
}
