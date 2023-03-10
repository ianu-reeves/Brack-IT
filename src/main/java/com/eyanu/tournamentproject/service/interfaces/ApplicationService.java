package com.eyanu.tournamentproject.service.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Application;
import com.eyanu.tournamentproject.entity.tournament.ApplicationId;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {
    Application findApplicationById(ApplicationId id);
    List<Application> findApplicationsByUser(User user);
    List<Application> findApplicationsByEvent(Event event);
    boolean canApply(User user, Event event);
    boolean hasApplied(User user, Event event);
    void apply(ApplicationId applicationId);
    void update(ApplicationId applicationId, ApplicationStatus applicationStatus);
    void delete(Application application);
    ApplicationStatus defaultApplicationStatus(Event event);
}
