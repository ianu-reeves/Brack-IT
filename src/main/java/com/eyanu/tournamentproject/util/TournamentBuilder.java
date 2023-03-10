package com.eyanu.tournamentproject.util;

import com.eyanu.tournamentproject.entity.tournament.Competitor;
import com.eyanu.tournamentproject.entity.tournament.Match;
import com.eyanu.tournamentproject.entity.tournament.Tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TournamentBuilder {
    private static List<Match> orderedMatches;
    private static Match rootMatch;
    private static Tournament mainTournament;
    private static int tournamentSize;
    private static int lastFullDepth;
    private static Iterator<Competitor> competitorIterator;

    private TournamentBuilder() {
    }

    // calculates the binary logarithmic value of the passed number
    public static double binaryLog(int x) {
        return Math.log(x)/ Math.log(2);
    }

    // invoke recursive generation of all matches. returns ordered list of all matches after they have
    // been linked and had x & y translation values applied
    public static List<Match> build(Tournament tournament) {
        mainTournament = tournament;
        orderedMatches = new ArrayList<>();
        rootMatch = new Match();
        tournamentSize = tournament.getCompetitors().size();
        lastFullDepth = (int) Math.floor(TournamentBuilder.binaryLog(tournamentSize));
        competitorIterator = tournament.getCompetitors().iterator();

        // generate matches, weave last full depth, and distribute extras as needed
        if (tournamentSize > 2) {
            List<Match> lastFullDepthMatches = Stream.concat(
                            createStringOfMatches(rootMatch, 1).stream(),
                            createStringOfMatches(rootMatch, 2).stream())
                    .collect(Collectors.toList());

            lastFullDepthMatches = weaveMatches(lastFullDepthMatches);
            populateMatches(lastFullDepthMatches);
            orderMatches();
        } else {
            populateMatchWithCompetitors(rootMatch);
        }
        return orderedMatches;
    }

    // orders and numbers all matches in BFS manner, beginning with the deepest 'left' match down
    // rootMatch's match1 branch and culminating with the root match

    private static void orderMatches() {
        List<Match> queue = new ArrayList<>();
        queue.add(rootMatch);

        for (int i = 0; i < tournamentSize - 1; i++) {
            Match nextMatch = queue.get(i);
            nextMatch.setMatchNumber(tournamentSize - i - 1);
            nextMatch.setTournament(mainTournament);
            if (nextMatch.getMatch2() != null) {
                queue.add(nextMatch.getMatch2());
            }
            if (nextMatch.getMatch1() != null) {
                queue.add(nextMatch.getMatch1());
            }
        }
        Collections.reverse(queue);
        orderedMatches = queue;
    }

    // recursively generate matches up to last full depth
    private static List<Match> createStringOfMatches(Match parent, int matchSlot){
        Match currentMatch = new Match();
        currentMatch.setMatchDepth(parent.getMatchDepth() + 1);
        linkMatches(parent, currentMatch, matchSlot);

        if (currentMatch.getMatchDepth() == lastFullDepth) {
            List<Match> matches = new ArrayList<>();
            matches.add(currentMatch);
            return matches;
        }
        List<Match> leftMatches = createStringOfMatches(currentMatch, 1);
        List<Match> rightMatches = createStringOfMatches(currentMatch, 2);
        return Stream.concat(leftMatches.stream(), rightMatches.stream()).collect(Collectors.toList());
    }

    // weaves list of matches such that e.g. (1, 2, 3, 4, 5, 6) -> (1, 4, 2, 5, 3, 6)
    private static List<Match> weaveMatches(List<Match> matches) {
        List<Match> wovenMatches = new ArrayList<>();
        int leftCounter = 0;
        int rightCounter = matches.size() / 2;

        while (leftCounter < matches.size()/2){
            wovenMatches.add(matches.get(leftCounter));
            wovenMatches.add(matches.get(rightCounter));

            leftCounter++;
            rightCounter++;
        }

        return wovenMatches;
    }

    // for each match in the list of matches at the last full depth, either generate extra matches to determine
    // the match's competitor(s) or populate the match with competitors directly
    private static void populateMatches(List<Match> matchesAtFullDepth) {
        int extraMatches = tournamentSize - (int) Math.pow(2, lastFullDepth);
        int index = 0;

        while (index < matchesAtFullDepth.size()) {
            Match match = matchesAtFullDepth.get(index);
            if (extraMatches > 0) {
                // create an extra match for both slots of given match if more than 1 extra match per match at LFD
                if (extraMatches > (int) Math.pow(2, lastFullDepth-1)) {
                    createExtraMatch(match, 1);
                } else {
                    match.setCompetitor1(competitorIterator.next());
                }
                createExtraMatch(match, 2);
                extraMatches--;
            } else {
                populateMatchWithCompetitors(match);
            }

            index++;
        }
    }

    // sets parent as child.parent & child as parent.match<matchSlot>
    private static void linkMatches(Match parent, Match child, int matchSlot) {
        child.setParent(parent);
        if (matchSlot == 1) {
            parent.setMatch1(child);
        } else if (matchSlot == 2) {
            parent.setMatch2(child);
        }
    }

    private static void populateMatchWithCompetitors(Match match) {
        if (competitorIterator.hasNext()) {
            Competitor competitor = competitorIterator.next();
            match.setCompetitor1(competitor);
        }
        if (competitorIterator.hasNext()) {
            Competitor competitor = competitorIterator.next();
            match.setCompetitor2(competitor);
        }
    }

    private static void createExtraMatch(Match parent, int parentSlotToPopulate) {
        Match firstRoundMatch = new Match();
        populateMatchWithCompetitors(firstRoundMatch);
        firstRoundMatch.setMatchDepth(parent.getMatchDepth() + 1);
        linkMatches(parent, firstRoundMatch, parentSlotToPopulate);
    }
}
