package br.com.escambo.app.control;

import br.com.escambo.app.model.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('adm'))")
    @PostMapping("/maintenance/on")
    public String enableMaintenance() {
        maintenanceService.setMaintenanceMode(true);
        return "redirect:/adm";
    }

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('adm'))")
    @PostMapping("/maintenance/off")
    public String disableMaintenance() {
        maintenanceService.setMaintenanceMode(false);
        return "redirect:/adm";
    }
}