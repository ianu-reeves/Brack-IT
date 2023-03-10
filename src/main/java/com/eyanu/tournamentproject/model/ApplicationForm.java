package com.eyanu.tournamentproject.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ApplicationForm {
    @NotNull
    @Min(1)
    private int userId;

    @NotNull
    @Min(1)
    private int eventId;

    public ApplicationForm() {
    }

    public ApplicationForm(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
