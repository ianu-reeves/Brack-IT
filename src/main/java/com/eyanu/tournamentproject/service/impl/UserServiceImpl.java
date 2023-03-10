package com.eyanu.tournamentproject.service.impl;

import com.eyanu.tournamentproject.dao.interfaces.RoleDao;
import com.eyanu.tournamentproject.dao.interfaces.UserDao;
import com.eyanu.tournamentproject.entity.user.Role;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.model.RegistrationForm;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public User getCurrentlyAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return userDao.findUserByUsername(auth.getName());
    }

    @Override
    @Transactional
    public void save(RegistrationForm registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(encoder.encode(registrationForm.getPassword()));
        user.setEmail(registrationForm.getEmail());
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setSteamId(registrationForm.getSteamId());
        user.setGamertag(registrationForm.getGamertag());
        user.setPsn(registrationForm.getPsn());
        user.setZoneId(ZoneId.of(registrationForm.getZoneId()));
        user.setRegion(registrationForm.getRegion());
        user.setBio(registrationForm.getBio());
        user.setSignupDate(LocalDate.now());
        user.addRole(roleDao.findRoleByName("ROLE_USER"));

        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
