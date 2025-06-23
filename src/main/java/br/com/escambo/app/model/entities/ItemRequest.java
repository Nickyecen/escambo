package br.com.escambo.app.model.entities;

import jakarta.persistence.*;

@Entity
public class ItemRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String itemname;

    private String category;
    private String volume;
    private String author;

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

    public String getCategory() {
         return category; 
    }
    public void setCategory(String category) {
         this.category = category; 
    }

    public String getVolume() {
         return volume; 
    }
    public void setVolume(String volume) {
         this.volume = volume; 
    }

    public String getAuthor() {
        return author; 
    }
    public void setAuthor(String author) {
         this.author = author; 
    }

    public String getRequestedBy() {
         return requestedBy; 
    }
    public void setRequestedBy(String requestedBy) { 
        this.requestedBy = requestedBy; 
    }
}