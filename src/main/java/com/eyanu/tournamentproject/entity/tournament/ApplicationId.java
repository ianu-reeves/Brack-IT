package com.eyanu.tournamentproject.entity.tournament;

import com.eyanu.tournamentproject.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApplicationId implements Serializable {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "event_id")
    private Event event;

    public ApplicationId() {
    }

    public ApplicationId(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationId that = (ApplicationId) o;
        return user.equals(that.user) && event.equals(that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, event);
    }
}
