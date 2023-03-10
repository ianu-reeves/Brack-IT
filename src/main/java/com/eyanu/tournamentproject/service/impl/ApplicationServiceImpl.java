package com.eyanu.tournamentproject.service.impl;

import com.eyanu.tournamentproject.dao.interfaces.ApplicationDao;
import com.eyanu.tournamentproject.entity.tournament.Application;
import com.eyanu.tournamentproject.entity.tournament.ApplicationId;
import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.ApplicationStatus;
import com.eyanu.tournamentproject.enums.EventType;
import com.eyanu.tournamentproject.service.interfaces.ApplicationService;
import com.eyanu.tournamentproject.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private EventService eventService;

    @Override
    @Transactional
    public Application findApplicationById(ApplicationId id) {
        return applicationDao.findApplicationById(id);
    }

    @Override
    @Transactional
    public List<Application> findApplicationsByUser(User user) {
        return applicationDao.findApplicationsByIdUser(user);
    }

    @Override
    @Transactional
    public List<Application> findApplicationsByEvent(Event event) {
        return applicationDao.findApplicationsByIdEvent(event);
    }

    @Override
    public boolean hasApplied(User user, Event event) {
        ApplicationId applicationId = new ApplicationId(user, event);
        for (Application application : event.getApplications()) {
            if (applicationId.equals(application.getApplicationId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canApply(User user, Event event) {
        if (user == null || user.equals(event.getCreator())) {
            return false;
        }

        Application application = findApplicationById(new ApplicationId(user, event));
        if (application != null) {
            return false;
        }

        return (event.getEventType() == EventType.OPEN || event.getEventType() == EventType.APPLY);
    }

    @Override
    @Transactional
    public void apply(ApplicationId applicationId) {
        Application application = new Application(applicationId);
        applicationId.getUser().addApplication(application);
        application.getApplicationId().getEvent().addApplication(application);

        applicationDao.save(application);
    }

    @Override
    @Transactional
    public void update(ApplicationId applicationId, ApplicationStatus applicationStatus) {
        Application application = applicationDao.findApplicationById(applicationId);
        application.setStatus(applicationStatus);

    }

    @Override
    @Transactional
    public void delete(Application application) {
        application.getApplicationId().getUser().removeApplication(application);
        application.getApplicationId().getEvent().removeApplication(application);
        applicationDao.delete(application);
    }

    @Override
    public ApplicationStatus defaultApplicationStatus(Event event) {
        if (eventService.isFull(event)) {
            return ApplicationStatus.PENDING;
        }
        return event.getEventType() == EventType.OPEN ? ApplicationStatus.ACCEPTED : ApplicationStatus.PENDING;
    }

}
