package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.user.User;

public interface UserDao {
    User findUserById(int id);
    User findUserByUsername(String username);
    void save(User user);
}
