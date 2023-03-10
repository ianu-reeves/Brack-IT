package com.eyanu.tournamentproject.entity.user;

import com.eyanu.tournamentproject.entity.game.Game;
import com.eyanu.tournamentproject.entity.tournament.*;
import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.util.ZoneIdConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private Region region;

    @Convert(converter = ZoneIdConverter.class)
    @Column(name = "zone_id")
    private ZoneId zoneId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "steam_id")
    private String steamId;

    @Column(name = "gamertag")
    private String gamertag;

    @Column(name = "psn")
    private String psn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "users_games",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games;

    @Column(name = "bio")
    private String bio;

    @Column(name = "signup_date")
    private LocalDate signupDate;

    @Column(name = "last_online")
    private OffsetDateTime lastOnline;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Competitor> tournamentEnrollments;

    @OneToMany(mappedBy = "applicationId.user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Application> applications;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<Character> characters;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Set<Tournament> tournaments;

    public User() {
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    public String getPsn() {
        return psn;
    }

    public void setPsn(String psn) {
        this.psn = psn;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    public OffsetDateTime getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(OffsetDateTime lastOnline) {
        this.lastOnline = lastOnline;
    }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public void removeApplication(Application application) {
        applications.remove(application);
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public void addTournamentEnrollment(Competitor competitor) {
        this.tournamentEnrollments.add(competitor);
    }

    public void removeTournamentEnrollment(Competitor competitor) {
        this.tournamentEnrollments.remove(competitor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
