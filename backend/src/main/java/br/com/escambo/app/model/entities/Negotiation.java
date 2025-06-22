package br.com.escambo.app.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity public class Negotiation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne @JoinColumn(name = "dispose_A_id", nullable = false)
    private Dispose disposeA;

    @JsonBackReference
    @ManyToOne @JoinColumn(name = "dispose_B_id", nullable = false)
    private Dispose disposeB;

    private boolean userAOk = false;

    private boolean userBOk = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUserAOk() {
        return userAOk;
    }

    public void setUserAOk(boolean userAOk) {
        this.userAOk = userAOk;
    }

    public Dispose getDisposeA() {
        return disposeA;
    }

    public void setDisposeA(Dispose disposeA) {
        this.disposeA = disposeA;
    }

    public Dispose getDisposeB() {
        return disposeB;
    }

    public void setDisposeB(Dispose disposeB) {
        this.disposeB = disposeB;
    }

    public boolean isUserBOk() {
        return userBOk;
    }

    public void setUserBOk(boolean userBOk) {
        this.userBOk = userBOk;
    }

    

}
