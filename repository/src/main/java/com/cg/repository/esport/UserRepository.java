package com.cg.repository.esport;

import com.cg.domain.esport.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String userName);
    Optional<User> findByUsername(String userName);

    User getByCodeSecurity(String code);
}
