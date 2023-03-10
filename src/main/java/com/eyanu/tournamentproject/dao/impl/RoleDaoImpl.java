package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.RoleDao;
import com.eyanu.tournamentproject.entity.user.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role findRoleByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        Query<Role> query = session.createQuery("FROM Role WHERE name=:roleName", Role.class);
        query.setParameter("roleName", name);

        Role role = null;

        try {
            role = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }
}
