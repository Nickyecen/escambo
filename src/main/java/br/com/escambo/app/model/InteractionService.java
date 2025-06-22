package br.com.escambo.app.model;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.entities.Interaction;


@Service
public class InteractionService {

    @Autowired private InteractionRepository interactionRepository;
    @Autowired UserService userService;


    @Transactional
    public boolean createInteraction(String type, Long targetId, Long sourceId) {
        User targetUser = userService.findById(targetId);
        User sourceUser = userService.findById(sourceId);
        if (targetUser == null || sourceUser == null) {
            return false; // Usuário não encontrado
        }

        Interaction interaction = new Interaction();
        interaction.setType(type);
        interaction.setTargetUsername(targetUser.getUsername());
        interaction.setSourceUsername(sourceUser.getUsername());
        interaction.setTimestamp(java.time.LocalDateTime.now().toString());

        // Aqui você pode salvar a interação no banco de dados, se necessário
        // interactionRepository.save(interaction);
        interactionRepository.save(interaction);
        return true;
    }

    public List<Interaction> findInteractionByTargetUsername(String targetUsername) {
        return interactionRepository.findByTargetUsername(targetUsername);
    }
    
}

