package com.eyanu.tournamentproject.entity.tournament;

import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.ApplicationStatus;
import com.eyanu.tournamentproject.enums.EventType;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "applications")
public class Application {

    @EmbeddedId
    private ApplicationId applicationId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "note")
    private String note;

    @Column(name = "application_time")
    private OffsetDateTime applicationTime;

    public Application() {
    }

    public Application(User user, Event event, ApplicationStatus status) {
        this.applicationId = new ApplicationId(user, event);
        this.status = status;
    }

    public Application(ApplicationId applicationId) {
        this.applicationId = applicationId;
        this.status = applicationId.getEvent().getDefaultApplicationStatus();
    }

    public ApplicationId getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ApplicationId applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return applicationId.equals(that.applicationId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId, status);
    }
}
