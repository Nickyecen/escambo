package br.com.escambo.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Negotiation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_s_id", nullable = false)
    private User userStarter;

    @ManyToOne @JoinColumn(name = "user_a_id", nullable = false)
    private User userAccepter;

    private boolean userSOk = false;

    private boolean userAOk = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserStarter() {
        return userStarter;
    }

    public void setUserStarter(User userStarter) {
        this.userStarter = userStarter;
    }

    public User getUserAccepter() {
        return userAccepter;
    }

    public void setUserAccepter(User userAccepter) {
        this.userAccepter = userAccepter;
    }

    public boolean isUserAOk() {
        return userAOk;
    }

    public void setUserAOk(boolean userAOk) {
        this.userAOk = userAOk;
    }

    public boolean isUserSOk() {
        return userSOk;
    }

    public void setUserSOk(boolean userSOk) {
        this.userSOk = userSOk;
    }
}
