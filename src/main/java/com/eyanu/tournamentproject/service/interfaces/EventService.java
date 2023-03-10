package com.eyanu.tournamentproject.service.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Competitor;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.SortMethod;
import com.eyanu.tournamentproject.enums.SortOrder;
import com.eyanu.tournamentproject.model.EventForm;

import java.util.List;
import java.util.Set;

public interface EventService {
    Event findEventById(int id);
    List<Event> findEventsByCreator(User creator);
    List<Event> findEventsByPage(int page, int count, SortMethod sortMethod, SortOrder order);
    int findLastPageNumber(int count);
    int save(EventForm event);
    void update(EventForm eventForm);
    void delete(int eventId);
    Set<Competitor> createCompetitorsForEvent(Event event);
    boolean isFull(Event event);
    void finalizeEvent(Event event);
}
