package br.com.escambo.app.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean maintenanceOn;
    private LocalDateTime timestamp;
    private String changedBy; // username do admin

    // getters e setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isMaintenanceOn() {
        return maintenanceOn;
    }
    public void setMaintenanceOn(boolean maintenanceOn) {
        this.maintenanceOn = maintenanceOn;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    
}