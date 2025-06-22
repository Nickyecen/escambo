package br.com.escambo.app.model;

import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {
    private volatile boolean maintenanceMode = false;

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }
}