package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.TournamentDao;
import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentDaoImpl implements TournamentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Tournament findTournamentById(int tournamentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Tournament> query = session.createQuery("FROM Tournament WHERE id=:tId", Tournament.class);
        query.setParameter("tId", tournamentId);

        Tournament tournament = null;
        try {
            tournament = query.getSingleResult();
        } catch (Exception e) {}
        return tournament;
    }

    @Override
    public List<Tournament> findTournamentsByOwner(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Tournament> query = session.createQuery("FROM Tournament WHERE creator=:owner", Tournament.class);
        query.setParameter("owner", user);

        return query.getResultList();
    }

    @Override
    public int save(Tournament tournament) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(tournament);
    }

    @Override
    public void delete(Tournament tournament) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tournament);
    }
}
