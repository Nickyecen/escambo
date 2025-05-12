package br.com.escambo.app.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity public class Dispose {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference
    @ManyToOne @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @JsonManagedReference
    @OneToMany(mappedBy = "disposeA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Negotiation> negotiationsA;

    @JsonManagedReference
    @OneToMany(mappedBy = "disposeB", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Negotiation> negotiationsB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

