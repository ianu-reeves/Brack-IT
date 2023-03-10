package com.eyanu.tournamentproject.dao.impl;

import com.eyanu.tournamentproject.dao.interfaces.MatchDao;
import com.eyanu.tournamentproject.entity.tournament.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDaoImpl implements MatchDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Match findMatchById(int matchId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Match> query = session.createQuery("FROM Match WHERE id=:mId", Match.class);
        query.setParameter("mId", matchId);

        Match match = null;
        try {
            match = query.getSingleResult();
        } catch (Exception e) {

        }
        return match;
    }

    @Override
    public void save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(match);
    }

    @Override
    public void delete(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(match);
    }
}
