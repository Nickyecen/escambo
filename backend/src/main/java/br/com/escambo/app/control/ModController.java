package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.escambo.app.model.UserService;

@RestController
@RequestMapping("/mods")
public class ModController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/{modId}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long modId, 
            @RequestParam("username") String usernameBanned) {
        boolean wasBanned = userService.banUserByUsername(modId, usernameBanned);
        // pega o id do moderador caso a gente queira registrar quem fez o banimento
        // Nao sei como vai funcionar o html, se for escrevendo o nome do usuario seria bom o booleano, caso o moderador escreva errado
        if (wasBanned) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}