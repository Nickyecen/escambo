package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.com.escambo.app.model.ModService;
import br.com.escambo.app.model.UserService;
import br.com.escambo.app.model.entities.Interaction;
import br.com.escambo.app.model.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
@RequestMapping("/mods")
public class ModController {

    @Autowired
    private ModService modService;

    @Autowired
    private UserService userService;

    @PostMapping("/{modId}/{itemName}/{approval}")
    @ResponseBody
    public ResponseEntity<Void> approveItem(@PathVariable Long modId,
                                            @PathVariable String itemName,
                                            @PathVariable boolean approval) {
        boolean wasApproved = modService.approveItem(modId, itemName, approval);
        if (wasApproved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{modId}/banhistory/{userId}")
    @ResponseBody
    public ResponseEntity<List<Interaction>> getBanHistory(@PathVariable Long modId,
                                                            @PathVariable Long userId) {
        List<Interaction> banHistory = modService.getBanHistory(modId, userId);
        if (banHistory == null || banHistory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(banHistory);
    }

    @PostMapping("/{modId}/ban")
    @ResponseBody
    public ResponseEntity<Void> banUser(@PathVariable Long modId,
                                        @RequestParam("username") String usernameBanned) {
        boolean wasBanned = modService.banUserByUsername(modId, usernameBanned);
        if (wasBanned) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{modId}/unban")
    @ResponseBody
    public ResponseEntity<Void> unbanUser(@PathVariable Long modId,
                                          @RequestParam("username") String usernameUnbanned) {
        boolean wasUnbanned = modService.unbanUserByUsername(modId, usernameUnbanned);
        if (wasUnbanned) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/moderator")
    public String showModeratorPage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("banHistory", modService.getAllBanLogs());
        User logged = userService.findByUsername(principal.getUsername());
        model.addAttribute("modId", logged.getId());

        return "pages/moderator";
    }
}
