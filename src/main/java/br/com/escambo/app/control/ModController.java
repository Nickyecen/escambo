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
    public String approveItem(@PathVariable("modId") Long modId,
                              @PathVariable("itemName") String itemName,
                              @PathVariable("approval") boolean approval) {
        modService.approveItem(modId, itemName, approval);
        return "redirect:/mods";
    }

    @PostMapping("/{modId}/removeItem")
    public String removeItem(@PathVariable("modId") Long modId,
                             @RequestParam("itemname") String itemName) {
        modService.removeItem(modId, itemName);
        return "redirect:/mods";
    }

    @GetMapping("/{modId}/banhistory/{userId}")
    @ResponseBody
    public ResponseEntity<List<Interaction>> getBanHistory(@PathVariable("modId") Long modId,
                                                            @PathVariable("userId") Long userId) {
        List<Interaction> banHistory = modService.getBanHistory(modId, userId);
        if (banHistory == null || banHistory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(banHistory);
    }

    @PostMapping("/{modId}/ban")
    public String banUser(@PathVariable("modId") Long modId,
                          @RequestParam("username") String usernameBanned) {
        modService.banUserByUsername(modId, usernameBanned);
        return "redirect:/mods";
    }

    @PostMapping("/{modId}/unban")
    public String unbanUser(@PathVariable("modId") Long modId,
                            @RequestParam("username") String usernameUnbanned) {
        modService.unbanUserByUsername(modId, usernameUnbanned);
        return "redirect:/mods";
    }

    @GetMapping()
    public String showModeratorPage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("banHistory", modService.getAllBanLogs());
        model.addAttribute("itemRequests", modService.getAllItemRequests());
        model.addAttribute("availableItems", modService.getAllItems());
        User logged = userService.findByUsername(principal.getUsername());
        model.addAttribute("modId", logged.getId());

        return "pages/moderator";
    }
}
