package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Match;

public interface MatchDao {
     Match findMatchById(int matchId);
     void save(Match match);
     void delete(Match match);
}
