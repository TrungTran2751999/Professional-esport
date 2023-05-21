package com.cg.repository.esport;

import com.cg.domain.esport.entities.Otp;
import com.cg.domain.esport.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
}
