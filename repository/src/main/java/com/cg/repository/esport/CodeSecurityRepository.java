package com.cg.repository.esport;

import com.cg.domain.esport.entities.SecurityCode;
import com.cg.domain.esport.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeSecurityRepository extends JpaRepository<SecurityCode, String> {
    SecurityCode getByUser(User user);
}
