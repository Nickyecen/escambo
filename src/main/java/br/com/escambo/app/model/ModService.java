package br.com.escambo.app.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.escambo.app.model.entities.User;

@Service
public class ModService {

    private final UserService userService;

    public ModService(UserService userService) {
        this.userService = userService;
    }
    @Transactional
    public boolean banUserByUsername(Long modId, String username) {
        User userBan = userService.findByUsername(username);
        if (userBan == null) {
            return false;
        }
        if(userBan.getRole().equals("MOD")) {
            return false; 
        }
        // registrar o ID do moderador que fez o banimento, se necessário
        // Ou ver se o moderador é válido, caso um usuario comum tenha acesso a /mods
        userBan.setBanned(true);
        userService.save(userBan);
        return true;
    }
}

