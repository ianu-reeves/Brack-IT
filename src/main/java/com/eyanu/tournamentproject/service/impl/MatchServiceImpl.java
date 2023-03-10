package com.eyanu.tournamentproject.service.impl;

import com.eyanu.tournamentproject.dao.interfaces.MatchDao;
import com.eyanu.tournamentproject.entity.tournament.Match;
import com.eyanu.tournamentproject.service.interfaces.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Override
    @Transactional
    public Match findMatchById(int matchId) {
        return matchDao.findMatchById(matchId);
    }

    @Override
    @Transactional
    public void save(Match match) {
        matchDao.save(match);
    }

    @Override
    @Transactional
    public void delete(Match match) {
        matchDao.delete(match);
    }

}
