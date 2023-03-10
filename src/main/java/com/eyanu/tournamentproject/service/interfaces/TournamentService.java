package com.eyanu.tournamentproject.service.interfaces;

import com.eyanu.tournamentproject.model.TournamentForm;
import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.entity.user.User;

import java.util.List;

public interface TournamentService {
    Tournament findTournamentById(int tournamentId);
    List<Tournament> findTournamentsByOwner(User user);
    int save(Tournament tournament);
    int saveNewFromForm(TournamentForm tournament);
    void delete(Tournament tournament);
}
