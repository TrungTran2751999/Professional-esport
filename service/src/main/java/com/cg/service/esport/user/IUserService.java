package com.cg.service.esport.user;

import com.cg.domain.esport.entities.User;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    User getByUsername(String username);
    User findByCodeSecurity(String code);
}
