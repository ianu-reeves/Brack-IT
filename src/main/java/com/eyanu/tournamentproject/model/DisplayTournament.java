package com.eyanu.tournamentproject.model;

import com.eyanu.tournamentproject.entity.tournament.Match;
import com.eyanu.tournamentproject.entity.tournament.Tournament;
import com.eyanu.tournamentproject.util.TournamentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisplayTournament {
    private Tournament tournament;

    // match transform values
    private static final int MATCH_WIDTH = 200;
    private static final int MATCH_HEIGHT = 50;
    private static final int MATCH_PADDING_V = 6;
    private static final int MATCH_PADDING_H = 50;

    private int TOTAL_WIDTH = MATCH_WIDTH + MATCH_PADDING_H;
    private int TOTAL_HEIGHT = MATCH_HEIGHT + MATCH_PADDING_V;

    // horizontal component of line between matches
    private int horizontalLineLength = 12;

    private List<DisplayMatch> displayMatches = new ArrayList<>();
    private List<String> bracketPaths = new ArrayList<>();

    public DisplayTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    // Create DisplayMatch objects for each Match in the Tournament as well as strings with SVG path values and commands
    // for drawing the connecting lines on the tree display. Adds each to their respective list
    public void generateDisplayObjects(int lastDepth) {
        if (lastDepth == 0) {
            return;
        }
        int tournamentMaxDepth = getMaxTournamentDepth();

        if (lastDepth > tournamentMaxDepth) {
            lastDepth = tournamentMaxDepth;
        }

        int initialXOffset = (lastDepth - 1) * TOTAL_WIDTH;
        int initialYOffset = getYOffset(lastDepth);

        int rootIndex = tournament.getMatches().size()-1;
        Match root = tournament.getMatches().get(rootIndex);

        transformDisplayElements(root, initialXOffset, initialYOffset, lastDepth);
    }

    private void transformDisplayElements(Match match, int xOffset, int yOffset, int lastDepth) {
        DisplayMatch displayMatch = new DisplayMatch(match, xOffset, yOffset);
        displayMatches.add(displayMatch);

        // check if paths for current match/ further matches need to be processed
        if (match.getMatchDepth() == lastDepth) {
            return;
        }

        int column = getMaxTournamentDepth() - match.getMatchDepth() + 1;
        int nextXOffset = xOffset - TOTAL_WIDTH;
        int nextYOffset = (getYOffset(column) - getYOffset(column - 1));

        int horizontalLineStartX = xOffset;
        int horizontalLineStartY = yOffset + (TOTAL_HEIGHT/2);
        int verticalLineX = horizontalLineStartX - horizontalLineLength;
        String path;

        // transformations/ paths for match2. includes path coming from parent match
        if (match.getMatch2() != null) {
            path = "M " + horizontalLineStartX + " " + horizontalLineStartY +
                    " L " + verticalLineX + " " + horizontalLineStartY +
                    " L " + verticalLineX + " " + (horizontalLineStartY + nextYOffset) +
                    " L " + (verticalLineX - horizontalLineLength) + " " + (horizontalLineStartY + nextYOffset);
            bracketPaths.add(path);
            transformDisplayElements(match.getMatch2(), nextXOffset, yOffset + nextYOffset, lastDepth);
        }

        if (match.getMatch1() != null) {
            path = " M " + verticalLineX + " " + horizontalLineStartY +
                    " L " + verticalLineX + " " + (horizontalLineStartY - nextYOffset) +
                    " L " + (verticalLineX - horizontalLineLength) + " " + (horizontalLineStartY - nextYOffset);
            bracketPaths.add(path);
            transformDisplayElements(match.getMatch1(), nextXOffset, yOffset - nextYOffset, lastDepth);
        }
    }

    public List<DisplayMatch> generateDisplayMatches(int lastDepth) {
        List<DisplayMatch> displayMatches;

        if (this.tournament == null) {
            return null;
        }

        int tournamentMaxDepth = getMaxTournamentDepth();

        if (lastDepth > tournamentMaxDepth) {
            lastDepth = tournamentMaxDepth;
        }

        int initialXOffset = (lastDepth - 1) * TOTAL_WIDTH;
        int initialYOffset;

        // if  all matches are being shown and extra match count <= number of matches at LFD, reduce
        // vertical translation factor by one column for aesthetics
//        if (lastDepth == tournamentMaxDepth) {
//            if (getExtraMatchCount() <= Math.pow(2, getLastFullDepth())) {
//                lastDepth -= 1;
//            }
//        }
        initialYOffset = getYOffset(lastDepth);
        int rootIndex = tournament.getMatches().size() - 1;
        Match root = tournament.getMatches().get(rootIndex);

        displayMatches = transformMatches(root, initialXOffset, initialYOffset, lastDepth);

        return displayMatches;
    }

    private List<DisplayMatch> transformMatches(Match match, int xOffset, int yOffset, int lastDepth) {
        List<DisplayMatch> displayMatches = new ArrayList<>();
        DisplayMatch displayMatch = new DisplayMatch(match, xOffset, yOffset);
        displayMatches.add(displayMatch);

        if (match.getMatchDepth() == lastDepth) {
            return displayMatches;
        }
        int nextXOffset = xOffset - TOTAL_WIDTH;
        int nextYOffset;
        int column = getMaxTournamentDepth() - match.getMatchDepth() + 1;

        if (match.getMatch1() != null) {
            nextYOffset = yOffset - (getYOffset(column) - getYOffset(column - 1));
            displayMatches = Stream.concat(displayMatches.stream(),
                    transformMatches(match.getMatch1(), nextXOffset, nextYOffset, lastDepth).stream())
                    .collect(Collectors.toList());
        }

        if (match.getMatch2() != null) {
            nextYOffset = yOffset + (getYOffset(column) - getYOffset(column - 1));
            displayMatches = Stream.concat(displayMatches.stream(),
                            transformMatches(match.getMatch2(), nextXOffset, nextYOffset, lastDepth).stream())
                    .collect(Collectors.toList());
        }

        return displayMatches;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public List<DisplayMatch> getDisplayMatches() {
        return displayMatches;
    }

    public List<String> getBracketPaths() {
        return bracketPaths;
    }

    private int getYOffset(int depth) {
        int offset = 0;
        for (int i = 1; i < depth; i++) {
            offset += TOTAL_HEIGHT * (Math.pow(2, i)/4);
        }

        return offset;
    }

    private int getLastFullDepth() {
        return (int) Math.floor(TournamentBuilder.binaryLog(tournament.getMatches().size()));
    }

    private int getExtraMatchCount() {
        return tournament.getMatches().size() - (int) Math.pow(2, getLastFullDepth());
    }

    public int getMaxTournamentDepth() {
        return (int) Math.ceil(TournamentBuilder.binaryLog(tournament.getMatches().size()));
    }

    public int getTOTAL_WIDTH() {
        return TOTAL_WIDTH;
    }

    public int getTOTAL_HEIGHT() {
        return TOTAL_HEIGHT;
    }

    public int getDisplayWidth() {
        return TOTAL_WIDTH * getMaxTournamentDepth();
    }

    public int getDisplayHeight() {
        return TOTAL_HEIGHT * (int) Math.pow(2, getMaxTournamentDepth()-1);
    }
}
