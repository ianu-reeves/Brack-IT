package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.ApplicationDao;
import com.eyanu.tournamentproject.entity.tournament.Application;
import com.eyanu.tournamentproject.entity.tournament.ApplicationId;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Application findApplicationById(ApplicationId id) {
        Session session = sessionFactory.getCurrentSession();
        Application application = null;
        Query<Application> query = session.createQuery(
                "FROM Application a WHERE a.applicationId.user=:uId AND a.applicationId.event=:eId",
                Application.class
        );
        query.setParameter("uId", id.getUser());
        query.setParameter("eId", id.getEvent());

        try {
            application = query.getSingleResult();
        } catch (Exception e) {
        }

        return application;
    }

    @Override
    @Transactional
    public List<Application> findApplicationsByIdUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Application> query = session.createQuery(
                "FROM Application a WHERE a.applicationId.user=:applicant",
                Application.class
        );
        query.setParameter("applicant", user);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Application> findApplicationsByIdEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        Query<Application> query = session.createQuery(
                "FROM Application a WHERE a.applicationId.event=:event",
                Application.class
        );
        query.setParameter("event", event);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Application application) {
        Session session = sessionFactory.getCurrentSession();
        session.save(application);
    }

    @Override
    @Transactional
    public void delete(Application application) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(application);
    }
}
