package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.com.escambo.app.model.ModService;
import br.com.escambo.app.model.entities.Interaction;

@Controller
@RequestMapping("/mods")
public class ModController {

    @Autowired
    private ModService modService;

    @PostMapping("/{modId}/{itemName}/{approval}")
    @ResponseBody
    public ResponseEntity<Void> approveItem(@PathVariable Long modId,
        @PathVariable String itemName, @PathVariable boolean approval) {
        boolean wasApproved = modService.approveItem(modId, itemName, approval);
        if (wasApproved) {
            return ResponseEntity.noContent().build();
        }
        else {
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
        // pega o id do moderador caso a gente queira registrar quem fez o banimento
        // Nao sei como vai funcionar o html, se for escrevendo o nome do usuario seria bom o booleano, caso o moderador escreva errado
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
    public String showModeratorPage(Model model) {
        return "pages/moderator";
    }
}
