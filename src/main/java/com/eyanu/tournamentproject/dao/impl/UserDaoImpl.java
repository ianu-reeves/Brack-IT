package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.UserDao;
import com.eyanu.tournamentproject.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE id=:id", User.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public User findUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username=:uName", User.class);
        query.setParameter("uName", username);

        User user = null;

        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }
}
