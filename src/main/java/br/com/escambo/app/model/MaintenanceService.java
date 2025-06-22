package br.com.escambo.app.model;

import br.com.escambo.app.model.entities.MaintenanceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MaintenanceService {
    private volatile boolean maintenanceMode = false;

    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode, String changedBy) {
        this.maintenanceMode = maintenanceMode;
        MaintenanceLog log = new MaintenanceLog();
        log.setMaintenanceOn(maintenanceMode);
        log.setTimestamp(LocalDateTime.now());
        log.setChangedBy(changedBy);
        maintenanceLogRepository.save(log);
    }
}