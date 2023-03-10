package com.eyanu.tournamentproject.service.impl;

import com.eyanu.tournamentproject.dao.interfaces.EventDao;
import com.eyanu.tournamentproject.entity.tournament.*;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.ApplicationStatus;
import com.eyanu.tournamentproject.enums.EventType;
import com.eyanu.tournamentproject.enums.SortMethod;
import com.eyanu.tournamentproject.enums.SortOrder;
import com.eyanu.tournamentproject.model.EventForm;
import com.eyanu.tournamentproject.service.interfaces.EventService;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import com.eyanu.tournamentproject.util.TournamentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Event findEventById(int id) {
        return eventDao.findEventById(id);
    }

    @Override
    @Transactional
    public List<Event> findEventsByCreator(User creator) {
        return findEventsByCreator(creator);
    }

    @Override
    @Transactional
    public List<Event> findEventsByPage(int page, int count, SortMethod sortMethod, SortOrder order) {
        return eventDao.findEventsByPage(page, count, sortMethod, order);
    }

    @Override
    @Transactional
    public int findLastPageNumber(int count) {
        return eventDao.findLastPageNumber(count);
    }

    @Override
    @Transactional
    public int save(EventForm eventForm) {
        Event event = new Event();
        User user = userService.getCurrentlyAuthenticatedUser();
        event.setCreator(user);
        event.setZoneId(ZoneId.of(eventForm.getZoneId()));
        event.setRegions(eventForm.getRegions());

        LocalTime startTime = LocalTime.of(eventForm.getStartHour(), eventForm.getStartMinute());
        Instant start = formatInstant(eventForm.getStartDate(), startTime, ZoneId.of(eventForm.getZoneId()));
        event.setStartTime(start);

        LocalTime enrollmentTime = LocalTime.of(eventForm.getEnrollmentHour(), eventForm.getEnrollmentHour());
        Instant deadline = formatInstant(eventForm.getEnrollmentDeadlineDate(), enrollmentTime, ZoneId.of(eventForm.getZoneId()));
        event.setEnrollmentDeadline(deadline);

        event.setTournament(
                new Tournament(eventForm.getName(), eventForm.getGame(), event, eventForm.getFirstTo())
        );
        event.setEventType(eventForm.getEventType());
        event.setCreationDate(Instant.now());
        event.setMaxCapacity(eventForm.getMaxCapacity());

        return eventDao.save(event);
    }

    @Override
    @Transactional
    public void update(EventForm eventForm) {
        Event event = eventDao.findEventById(eventForm.getId());
        ZoneId zone = ZoneId.of(eventForm.getZoneId());
        Tournament tournament = event.getTournament();
        event.setZoneId(zone);
        event.setStartTime(
            formatInstant(
                eventForm.getStartDate(), LocalTime.of(eventForm.getStartHour(), eventForm.getStartMinute()), zone
            )
        );
        event.setEnrollmentDeadline(
            formatInstant(
                eventForm.getEnrollmentDeadlineDate(), LocalTime.of(eventForm.getEnrollmentHour(), eventForm.getEnrollmentMinute()), zone
            )
        );
        tournament.setName(eventForm.getName());
        tournament.setFirstTo(eventForm.getFirstTo());
        event.setMaxCapacity(eventForm.getMaxCapacity());
        event.setRegions(eventForm.getRegions());
        event.setEventType(eventForm.getEventType());

        eventDao.save(event);
    }

    @Override
    @Transactional
    public void delete(int eventId) {
        eventDao.delete(eventId);
    }

    @Override
    public Set<Competitor> createCompetitorsForEvent(Event event) {
        Set<Competitor> competitors = new HashSet<>();
        for(Application application : event.getApplications()) {
            if (application.getStatus() == ApplicationStatus.ACCEPTED) {
                competitors.add(new Competitor(application.getApplicationId().getUser()));
            }
        }
        return competitors;
    }

    @Override
    public boolean isFull(Event event) {
        return event.getApplications().size() >= event.getMaxCapacity();
    }

    // set event's Type to CLOSED, creates matches/ competitors for its tournament
    @Override
    public void finalizeEvent(Event event) {
        Tournament tournament = event.getTournament();
        event.setEventType(EventType.CLOSED);
        tournament.setCompetitors(createCompetitorsForEvent(event));
        List<Match> matches = TournamentBuilder.build(tournament);
        tournament.setMatches(matches);

        eventDao.save(event);
    }

    private Instant formatInstant(LocalDate date, LocalTime time, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.of(date, time);
        return ldt.atZone(zoneId).toInstant();
    }

}
