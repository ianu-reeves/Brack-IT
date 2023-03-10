package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.entity.user.User;

import java.util.List;

public interface TournamentDao {
    Tournament findTournamentById(int tournamentId);
    List<Tournament> findTournamentsByOwner(User user);
    int save(Tournament tournament);
    void delete(Tournament tournament);
}
