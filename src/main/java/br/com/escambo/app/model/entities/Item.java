package br.com.escambo.app.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String itemname;

    @JsonManagedReference
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dispose> disposetions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public List<Dispose> getDisposetions() {
        return disposetions;
    }

    public void setDisposetions(List<Dispose> disposetions) {
        this.disposetions = disposetions;
    }
}
