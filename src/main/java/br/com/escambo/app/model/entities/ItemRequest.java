package br.com.escambo.app.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class ItemRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String itemname;

    private String requestedBy; // username do solicitante

    // getters e setters
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
    public String getRequestedBy() {
        return requestedBy;
    }
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

}