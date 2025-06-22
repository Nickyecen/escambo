package br.com.escambo.app.control;

import br.com.escambo.app.model.NotificationRepository;
import br.com.escambo.app.model.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String viewNotifications(Model model, Principal principal) {
        List<Notification> notifications = notificationRepository.findByUsernameOrderByTimestampDesc(principal.getName());
        model.addAttribute("notifications", notifications);
        return "pages/notifications";
    }
}