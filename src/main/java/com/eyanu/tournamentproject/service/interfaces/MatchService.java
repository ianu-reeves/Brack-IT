package com.eyanu.tournamentproject.service.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Match;

public interface MatchService {
    Match findMatchById(int matchId);
    void save(Match match);
    void delete(Match match);
}
