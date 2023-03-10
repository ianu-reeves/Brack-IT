package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.EventDao;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.SortMethod;
import com.eyanu.tournamentproject.enums.SortOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Event findEventById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Event> query = session.createQuery("FROM Event WHERE id=:eId", Event.class);
        query.setParameter("eId", id);

        return query.getSingleResult();
    }

    @Override
    public List<Event> findEventsByCreator(User creator) {
        Session session = sessionFactory.getCurrentSession();
        Query<Event> query = session.createQuery("FROM Event WHERE creator=:c", Event.class);
        query.setParameter("c", creator);

        return query.getResultList();
    }

    @Override
    public List<Event> findEventsByPage(int page, int count, SortMethod sortMethod, SortOrder order) {
        Session session = sessionFactory.getCurrentSession();
        Query<Event> query = session.createQuery("FROM Event ORDER BY "+sortMethod+" "+order, Event.class);
        query.setFirstResult(page - 1);
        query.setMaxResults(count);

        return query.getResultList();
    }

    @Override
    public int findLastPageNumber(int count) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> countQuery = session.createQuery("SELECT COUNT (*) FROM Event", Long.class);
        Long result = countQuery.uniqueResult();
        return (int) Math.ceil(result/ (double) count);
    }

    @Override
    public int save(Event event) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(event);
    }

    @Override
    public void delete(int eventId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Event WHERE id=:id");
        query.setParameter("id", eventId);
        query.executeUpdate();
    }
}
