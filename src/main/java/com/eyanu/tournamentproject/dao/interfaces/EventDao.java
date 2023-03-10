package com.eyanu.tournamentproject.dao.interfaces;

import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.SortMethod;
import com.eyanu.tournamentproject.enums.SortOrder;

import java.util.List;

//TODO
// add findEventByDate

public interface EventDao {
    Event findEventById(int id);
    List<Event> findEventsByCreator(User creator);
    List<Event> findEventsByPage(int page, int count, SortMethod sortMethod, SortOrder order);
    int findLastPageNumber(int count);
    int save(Event event);
    void delete(int eventId);
}
