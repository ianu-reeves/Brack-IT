package com.eyanu.tournamentproject.entity.tournament;

import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.enums.ApplicationStatus;
import com.eyanu.tournamentproject.enums.EventType;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.util.ZoneIdConverter;

import javax.persistence.*;
import java.time.*;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Convert(converter = ZoneIdConverter.class)
    @Column(name = "zone_id")
    private ZoneId zoneId;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "enrollment_deadline")
    private Instant enrollmentDeadline;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "max_capacity")
    private int maxCapacity;

    // applications made to the tournament
    @OneToMany(mappedBy = "applicationId.event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Application> applications;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @Column(name = "region")
    private Set<Region> regions;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    public Event() {
    }

    public ApplicationStatus getDefaultApplicationStatus() {
        if (eventType == EventType.CLOSED) {
            return ApplicationStatus.REJECTED;
        }
        if (applications.size() >= maxCapacity) {
            return ApplicationStatus.PENDING;
        }
        return eventType == EventType.OPEN ? ApplicationStatus.ACCEPTED : ApplicationStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zone) {
        this.zoneId = zone;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant time) {
        this.startTime = time;
    }

    public Instant getEnrollmentDeadline() {
        return enrollmentDeadline;
    }

    public void setEnrollmentDeadline(Instant enrollmentDeadline) {
        this.enrollmentDeadline = enrollmentDeadline;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public void removeApplication(Application application) {
        this.applications.remove(application);
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public void addRegion(Region region) {
        this.regions.add(region);
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
