package com.eyanu.tournamentproject.entity.tournament;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @OneToOne
    @JoinColumn(name = "parent_match_id")
    private Match parent;

    // child entities
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_slot_1_id")
    private Match match1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_slot_2_id")
    private Match match2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "competitor_1_id")
    private Competitor userCompetitor1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "competitor_2_id")
    private Competitor userCompetitor2;

    @Column(name = "match_depth")
    private int matchDepth = 1;

    // match number within a single tournament. Ranges from 1 - tournamentSize where latter is rootMatch
    @Column(name = "match_number")
    private int matchNumber;

    @OneToOne
    @JoinColumn(name = "winner")
    private Competitor winner;

    // constructors
    public Match() {
    }

    // getters & setters

    public int getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Match getParent() {
        return parent;
    }

    public void setParent(Match parent) {
        this.parent = parent;
    }

    public Match getMatch1() {
        return match1;
    }

    public void setMatch1(Match match1) {
        this.match1 = match1;
    }

    public Match getMatch2() {
        return match2;
    }

    public void setMatch2(Match match2) {
        this.match2 = match2;
    }

    public Competitor getCompetitor1() {
        return userCompetitor1;
    }

    public void setCompetitor1(Competitor userCompetitor1) {
        this.userCompetitor1 = userCompetitor1;
    }

    public Competitor getCompetitor2() {
        return userCompetitor2;
    }

    public void setCompetitor2(Competitor userCompetitor2) {
        this.userCompetitor2 = userCompetitor2;
    }

    public int getMatchDepth() {
        return matchDepth;
    }

    public void setMatchDepth(int matchDepth) {
        this.matchDepth = matchDepth;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public Competitor getWinner() {
        return winner;
    }

    public void setWinner(Competitor winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", tournament=" + tournament +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id == match.id && Objects.equals(tournament, match.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tournament);
    }

//    @Component
//    public static class TournamentBuilder {
//        private List<Match> orderedMatches;
//        private Match rootMatch;
//        private Tournament tournament;
//        private int tournamentSize;
//        private int maxDepth;
//        private int lastFullDepth;
//        private Iterator<Competitor> competitorIterator;
//
//        // invoke recursive generation of all matches. returns ordered list of all matches after they have
//        // been linked and had x & y translation values applied
//        public List<Match> build(Tournament tournament, Collection<Competitor> competitors) {
//            this.tournament = tournament;
//            orderedMatches = new ArrayList<>();
//            rootMatch = new Match();
//            tournamentSize = competitors.size();
//            maxDepth = (int) Math.ceil(TournamentUtil.binaryLog(tournamentSize));
//            lastFullDepth = (int) Math.floor(TournamentUtil.binaryLog(tournamentSize));
//            competitorIterator = competitors.iterator();
//
//            // generate matches, weave last full depth, and distribute extras as needed
//            if (tournamentSize > 2) {
//                List<Match> lastFullDepthMatches = Stream.concat(
//                        createStringOfMatches(rootMatch, 1).stream(),
//                        createStringOfMatches(rootMatch, 2).stream())
//                        .collect(Collectors.toList());
//
//                lastFullDepthMatches = weaveMatches(lastFullDepthMatches);
//                populateMatches(lastFullDepthMatches);
//                orderMatches();
//            } else {
//                populateMatchWithCompetitors(rootMatch);
//            }
//            return orderedMatches;
//        }
//
//        // orders and numbers all matches in BFS manner, beginning with the deepest 'left' match down
//        // rootMatch's match1 branch and culminating with the root match
//
//        private void orderMatches() {
//            List<Match> queue = new ArrayList<>();
//            queue.add(rootMatch);
//
//            for (int i = 0; i < tournamentSize - 1; i++) {
//                Match nextMatch = queue.get(i);
//                nextMatch.setMatchNumber(tournamentSize - i - 1);
//                nextMatch.setTournament(tournament);
//                if (nextMatch.getMatch2() != null) {
//                    queue.add(nextMatch.getMatch2());
//                }
//                if (nextMatch.getMatch1() != null) {
//                    queue.add(nextMatch.getMatch1());
//                }
//            }
//            Collections.reverse(queue);
//            orderedMatches = queue;
//        }
//
//        // recursively generate matches up to last full depth
//        private List<Match> createStringOfMatches(Match parent, int matchSlot){
//            Match currentMatch = new Match();
//            currentMatch.setMatchDepth(parent.getMatchDepth() + 1);
//            linkMatches(parent, currentMatch, matchSlot);
//
//            if (currentMatch.getMatchDepth() == lastFullDepth) {
//                List<Match> matches = new ArrayList<>();
//                matches.add(currentMatch);
//                return matches;
//            }
//            List<Match> leftMatches = createStringOfMatches(currentMatch, 1);
//            List<Match> rightMatches = createStringOfMatches(currentMatch, 2);
//            return Stream.concat(leftMatches.stream(), rightMatches.stream()).collect(Collectors.toList());
//        }
//
//        // weaves list of matches such that e.g. (1, 2, 3, 4, 5, 6) -> (1, 4, 2, 5, 3, 6)
//        private List<Match> weaveMatches(List<Match> matches) {
//            List<Match> wovenMatches = new ArrayList<>();
//            int leftCounter = 0;
//            int rightCounter = matches.size() / 2;
//
//            while (leftCounter < matches.size()/2){
//                wovenMatches.add(matches.get(leftCounter));
//                wovenMatches.add(matches.get(rightCounter));
//
//                leftCounter++;
//                rightCounter++;
//            }
//
//            return wovenMatches;
//        }
//
//        // for each match in the list of matches at the last full depth, either generate extra matches to determine
//        // the match's competitor(s) or populate the match with competitors directly
//        private void populateMatches(List<Match> matchesAtFullDepth) {
//            int extraMatches = tournamentSize - (int) Math.pow(2, lastFullDepth);
//            int index = 0;
//
//            while (index < matchesAtFullDepth.size()) {
//                Match match = matchesAtFullDepth.get(index);
//                if (extraMatches > 0) {
//                    if (extraMatches > (int) Math.pow(2, lastFullDepth-1)) {
//                        createExtraMatch(match, 1);
//                    } else {
//                        match.setCompetitor1(competitorIterator.next());
//                    }
//                    createExtraMatch(match, 2);
//                    extraMatches--;
//                } else {
//                    populateMatchWithCompetitors(match);
//                }
//
//                index++;
//            }
//        }
//
//        private void linkMatches(Match parent, Match child, int parentMatchNumber) {
//            child.setParent(parent);
//            if (parentMatchNumber == 1) {
//                parent.setMatch1(child);
//            } else if (parentMatchNumber == 2) {
//                parent.setMatch2(child);
//            }
//        }
//
//        private void populateMatchWithCompetitors(Match match) {
//            if (competitorIterator.hasNext()) {
//                Competitor competitor = competitorIterator.next();
//                match.setCompetitor1(competitor);
//            }
//            if (competitorIterator.hasNext()) {
//                Competitor competitor = competitorIterator.next();
//                match.setCompetitor2(competitor);
//            }
//        }
//
//        private void createExtraMatch(Match parent, int parentSlotToPopulate) {
//            Match firstRoundMatch = new Match();
//            populateMatchWithCompetitors(firstRoundMatch);
//            firstRoundMatch.setMatchDepth(parent.getMatchDepth() + 1);
//            linkMatches(parent, firstRoundMatch, parentSlotToPopulate);
//        }
//    }
}
