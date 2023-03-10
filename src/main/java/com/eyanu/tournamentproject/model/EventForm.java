package com.eyanu.tournamentproject.model;

import com.eyanu.tournamentproject.entity.tournament.Event;
import com.eyanu.tournamentproject.enums.EventType;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.validation.AfterStartDate;
import com.eyanu.tournamentproject.validation.ValidZone;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.*;
import java.util.Set;

@AfterStartDate
public class EventForm {

    int id;

    @Size(min = 3, max = 256, message = "Event name must be between 3 and 256 characters")
    @NotNull
    private String name;

    @Size(min = 3, max = 256, message = "Name of game cannot exceed 256 characters")
    @NotNull
    private String game;

    @NotNull
    @ValidZone
    private String zoneId;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate startDate;

    @Range(min = 0, max = 23)
    private int startHour;

    @Range(min = 0, max = 59)
    private int startMinute;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate enrollmentDeadlineDate;

    @Range(min = 0, max = 24)
    private int enrollmentHour;

    @Range(min = 0, max = 59)
    private int enrollmentMinute;

    @Min(value = 2, message = "Minimum of 2 entrants")
    @Max(value = 256, message = "Maximum of 256 entrants")
    private int maxCapacity;

    @Min(value = 1, message = "Minimum of 1 win")
    @Max(value = 7, message = "Maximum of 7 wins")
    private int firstTo;

    private Set<Region> regions;

    @NotNull
    private EventType eventType;

    public EventForm() {
    }

    public EventForm(Event event) {
        this.setId(event.getId());
        this.setZoneId(event.getZoneId().getId());
        this.setStartDate(event.getStartTime().atZone(event.getZoneId()).toLocalDate());
        this.setEnrollmentDeadlineDate(event.getEnrollmentDeadline().atZone(event.getZoneId()).toLocalDate());

        LocalTime startTime = event.getStartTime().atZone(event.getZoneId()).toLocalTime();
        this.setStartHour(startTime.getHour());
        this.setStartMinute(startTime.getMinute());

        LocalTime enrollmentTime = event.getEnrollmentDeadline().atZone(event.getZoneId()).toLocalTime();
        this.setEnrollmentHour(enrollmentTime.getHour());
        this.setEnrollmentMinute(enrollmentTime.getMinute());

        this.setMaxCapacity(event.getMaxCapacity());
        this.setRegions(event.getRegions());
        this.setEventType(event.getEventType());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public LocalDate getEnrollmentDeadlineDate() {
        return enrollmentDeadlineDate;
    }

    public void setEnrollmentDeadlineDate(LocalDate enrollmentDeadlineDate) {
        this.enrollmentDeadlineDate = enrollmentDeadlineDate;
    }

    public int getEnrollmentHour() {
        return enrollmentHour;
    }

    public void setEnrollmentHour(int enrollmentHour) {
        this.enrollmentHour = enrollmentHour;
    }

    public int getEnrollmentMinute() {
        return enrollmentMinute;
    }

    public void setEnrollmentMinute(int enrollmentMinute) {
        this.enrollmentMinute = enrollmentMinute;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getFirstTo() {
        return firstTo;
    }

    public void setFirstTo(int firstTo) {
        this.firstTo = firstTo;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
