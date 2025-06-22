package br.com.escambo.app.control;

import br.com.escambo.app.model.MaintenanceService;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.User;
import org.springframework.ui.Model;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/adm")
public class AdmController {

    @Autowired private UserRepository userRepository;
    @Autowired private MaintenanceService maintenanceService;

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('adm'))")
    @PostMapping("/maintenance/on")
    public String enableMaintenance(Principal principal) {
        maintenanceService.setMaintenanceMode(true, principal.getName());
        return "redirect:/adm";
    }

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('adm'))")
    @PostMapping("/maintenance/off")
    public String disableMaintenance(Principal principal) {
        maintenanceService.setMaintenanceMode(false, principal.getName());
        return "redirect:/adm";
    }

    // Lista todos os usuários e moderadores
    @GetMapping("/moderators")
    public String listModerators(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "pages/moderators";
    }

    // Promove usuário a moderador
    @PostMapping("/moderators/add")
    public String addModerator(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setRole("mod");
            userRepository.save(user);
        }
        return "redirect:/adm/moderators";
    }

    // Remove papel de moderador
    @PostMapping("/moderators/remove")
    public String removeModerator(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "mod".equals(user.getRole())) {
            user.setRole("usr");
            userRepository.save(user);
        }
        return "redirect:/adm/moderators";
    }

}