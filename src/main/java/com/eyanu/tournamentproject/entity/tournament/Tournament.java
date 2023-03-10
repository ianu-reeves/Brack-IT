package com.eyanu.tournamentproject.entity.tournament;

import com.eyanu.tournamentproject.entity.user.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "game")
    private String game;

    @OneToOne
    private Event event;

    @Column(name = "first_to")
    private int firstTo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("matchNumber")
    private List<Match> matches;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tournament_id")
    private Set<Competitor> competitors;

    public Tournament() {
    }

    public Tournament(String name, String game, Event event, int firstTo) {
        this.name = name;
        this.game = game;
        this.event = event;
        this.firstTo = firstTo;
    }

    public Tournament(Event event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getFirstTo() {
        return firstTo;
    }

    public void setFirstTo(int numberOfSets) {
        this.firstTo = numberOfSets;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Set<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<Competitor> competitors) {
        this.competitors = competitors;
    }

    public void addMatch(Match match) {
        matches.add(match);
        match.setTournament(this);
    }

    public void removeMatch(Match match) {
        matches.remove(match);
        match.setTournament(null);
    }

    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public void removeCompetitor(Competitor competitor) {
        competitors.remove(competitor);
    }

}
