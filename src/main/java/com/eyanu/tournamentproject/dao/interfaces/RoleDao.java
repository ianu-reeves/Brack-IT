package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.user.Role;

public interface RoleDao {
    Role findRoleByName(String name);
}
