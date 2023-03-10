package com.eyanu.tournamentproject.entity.tournament;

import com.eyanu.tournamentproject.entity.game.Character;
import com.eyanu.tournamentproject.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "competitors")
@DiscriminatorValue("not null")
public class Competitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score")
    private int score = 0;

    @Column(name = "name")
    private String name;

    public Competitor() {
    }

    public Competitor(User user) {
        this.user = user;
        this.name = user.getUsername();
    }

    public Competitor(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.addTournamentEnrollment(this);
    }

    public void detachUser() {
        if (this.user != null) {
            this.user.removeTournamentEnrollment(this);
            this.user = null;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        if (this.user != null) {
            return user.getUsername();
        }

        return name;
    }

    public void setName(String name) {
        if (this.user != null) {
            this.name = name;
        }
    }

}
