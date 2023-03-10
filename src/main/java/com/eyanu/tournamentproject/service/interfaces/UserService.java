package com.eyanu.tournamentproject.service.interfaces;

import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.model.RegistrationForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findUserById(int it);
    User findUserByUsername(String username);
    User getCurrentlyAuthenticatedUser();
    void save(RegistrationForm registrationForm);
}
