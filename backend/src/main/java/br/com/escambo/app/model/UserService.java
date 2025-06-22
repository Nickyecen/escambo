package br.com.escambo.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escambo.app.model.entities.User;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void deleteUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }

        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean banUserByUsername(Long modId, String username) {
        User userBan = userRepository.findByUsername(username);
        if (userBan == null) {
            return false;
        }
        // registrar o ID do moderador que fez o banimento, se necessário
        // Ou ver se o moderador é válido, caso um usuario comum tenha acesso a /mods
        userBan.setBanned(true);
        userRepository.save(userBan);
        return true;
    }
}

