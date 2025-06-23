package br.com.escambo.app.control;

import br.com.escambo.app.model.MaintenanceService;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.MaintenanceLogRepository;
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
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @Autowired private MaintenanceLogRepository maintenanceLogRepository;

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('ROLE_ADMIN'))")
    @PostMapping("/maintenance/on")
    public String enableMaintenance(Principal principal) {
        maintenanceService.setMaintenanceMode(true, principal.getName());
        return "redirect:/adm/administrator";
    }

    @PreAuthorize("authentication.principal.authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority('ROLE_ADMIN'))")
    @PostMapping("/maintenance/off")
    public String disableMaintenance(Principal principal) {
        maintenanceService.setMaintenanceMode(false, principal.getName());
        return "redirect:/adm/administrator";
    }

    // Painel admin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/administrator")
    public String adminPanel(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("maintenanceMode", maintenanceService.isMaintenanceMode());
        model.addAttribute("maintenanceLogs", maintenanceLogRepository.findAll());
        return "pages/administrator";
    }

    // Promove usuário a moderador
    @PostMapping("/administrator/moderators/add")
    public String addModerator(@RequestParam("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setRole("ROLE_MOD");
            userRepository.save(user);
        }
        return "redirect:/adm/administrator";
    }

    // Remove papel de moderador
    @PostMapping("/administrator/moderators/remove")
    public String removeModerator(@RequestParam("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "ROLE_MOD".equals(user.getRole())) {
            userRepository.delete(user);
        }
        return "redirect:/adm/administrator";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/administrator/moderators/create")
    public String createModerator(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Já existe um usuário com esse nome.");
            model.addAttribute("username", username);
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("maintenanceMode", maintenanceService.isMaintenanceMode());
            return "pages/administrator";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_MOD");
        userRepository.save(user);

        return "redirect:/adm/administrator";
    }

}
