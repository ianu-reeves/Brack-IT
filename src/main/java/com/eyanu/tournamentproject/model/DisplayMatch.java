package com.eyanu.tournamentproject.model;

import com.eyanu.tournamentproject.entity.tournament.Match;

import java.util.Objects;

public class DisplayMatch {
    private Match match;
    private int xOffset;
    private int yOffset;

    public DisplayMatch() {
    }

    public DisplayMatch(Match match, int xOffset, int yOffset) {
        this.match = match;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayMatch that = (DisplayMatch) o;
        return xOffset == that.xOffset && yOffset == that.yOffset && match.equals(that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(match, xOffset, yOffset);
    }

    @Override
    public String toString() {
        return "DisplayMatch{" +
                "match=" + match +
                ", xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                '}';
    }
}
