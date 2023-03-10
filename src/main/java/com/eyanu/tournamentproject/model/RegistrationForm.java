package com.eyanu.tournamentproject.model;

import com.eyanu.tournamentproject.enums.Region;
import com.eyanu.tournamentproject.validation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZoneId;

@FieldMatch.List({
        @FieldMatch(firstField = "password", secondField = "matchingPassword", message = "Passwords must match")
})
public class RegistrationForm {

    @NotNull
    @Size(min = 3, max = 24)
    private String username;

    @NotNull
    @Size(min = 10, max = 50, message = "Password must be between 10 and 50 characters")
    private String password;

    @NotNull
    @Size(min = 10, max = 50)
    private String matchingPassword;

    @Size(min = 17, max= 17, message = "Invalid Steam ID")
    private String steamId;

    @ValidGamertag
    private String gamertag;

    @ValidPsn
    private String psn;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidZone
    private String zoneId;

    @NotNull(message = "Region is required")
    private Region region;

    @Size(max = 16, message = "First name must be less than 16 characters")
    private String firstName;

    @Size(max = 24, message = "Last name must be less than 24 characters")
    private String lastName;

    @Size(max = 2000, message = "Bio must be less than 2,000 characters")
    private String bio;

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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
