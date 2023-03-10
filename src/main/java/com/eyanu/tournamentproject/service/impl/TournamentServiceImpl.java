package com.eyanu.tournamentproject.service.impl;

import com.eyanu.tournamentproject.dao.interfaces.TournamentDao;
import com.eyanu.tournamentproject.model.TournamentForm;
import com.eyanu.tournamentproject.entity.tournament.*;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.service.interfaces.TournamentService;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import com.eyanu.tournamentproject.util.TournamentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Tournament findTournamentById(int tournamentId) {
        return tournamentDao.findTournamentById(tournamentId);
    }

    @Override
    public int save(Tournament tournament) {
        return tournamentDao.save(tournament);
    }

    @Override
    @Transactional
    public int saveNewFromForm(TournamentForm tournamentForm) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentForm.getName());
        tournament.setCompetitors(generateDummyCompetitorListFromInt(tournamentForm.getCompetitorCount()));
        List<Match> matches = TournamentBuilder.build(tournament);
        tournament.setMatches(matches);

        return tournamentDao.save(tournament);
    }

    @Override
    @Transactional
    public List<Tournament> findTournamentsByOwner(User user) {
        return tournamentDao.findTournamentsByOwner(user);
    }

    @Override
    @Transactional
    public void delete(Tournament tournament) {
        tournamentDao.delete(tournament);
    }

    public Set<Competitor> generateDummyCompetitorListFromInt(int participantCount) {
        Set<Competitor> competitors = new LinkedHashSet<>();
        for (int i = 0; i < participantCount; i++) {
            competitors.add(new Competitor(Integer.toString(i+1)));
        }

        return competitors;
    }

    public Set<Competitor> generateDummyCompetitorListFromNames(String nameList) {
        String[] names = nameList.split("\n");
        return Arrays.stream(names).map(Competitor::new).collect(Collectors.toSet());
    }

    public Set<Competitor> generatePilotedCompetitorList(Set<User> users) {
        return  users.stream().map(Competitor::new).collect(Collectors.toSet());
    }

}
