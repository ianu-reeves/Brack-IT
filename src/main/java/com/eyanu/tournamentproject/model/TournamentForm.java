package com.eyanu.tournamentproject.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TournamentForm {

    @NotNull
    @Size(min = 3, max = 64, message = "Tournament name must be between 3 and 64 characters")
    private String name;

    @Size(min = 3, max = 256, message = "Game title must be between 3 and 256 characters")
    private String game;

    @Min(value = 0, message = "Cannot have negative number of competitors")
    @Max(value = 256, message = "You may have a maximum of 256 competitors")
    private int competitorCount;

    //TODO: Add validator to ensure <= 256 lines entered & each line is valid name
    private String competitorNames;

    public TournamentForm() {
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

    public int getCompetitorCount() {
        return competitorCount;
    }

    public void setCompetitorCount(int competitorCount) {
        this.competitorCount = competitorCount;
    }

    public String getCompetitorNames() {
        return competitorNames;
    }

    public void setCompetitorNames(String competitorNames) {
        this.competitorNames = competitorNames;
    }
}
