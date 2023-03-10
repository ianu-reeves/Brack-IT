package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Application;
import com.eyanu.tournamentproject.entity.tournament.ApplicationId;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;

import java.util.List;

public interface ApplicationDao {
    Application findApplicationById(ApplicationId id);
    List<Application> findApplicationsByIdUser(User user);
    List<Application> findApplicationsByIdEvent(Event event);
    void save(Application application);
    void delete(Application application);
}
