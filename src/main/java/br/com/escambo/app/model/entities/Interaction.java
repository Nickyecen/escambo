package br.com.escambo.app.model.entities;


import jakarta.persistence.*;

@Entity
public class Interaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // e.g., "ban", "unban"
    private String targetUsername; 
    private String sourceUsername; 
    private String timestamp; 

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
