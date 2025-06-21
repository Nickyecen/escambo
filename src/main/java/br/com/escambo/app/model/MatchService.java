package br.com.escambo.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.escambo.app.model.entities.*;
import java.util.List;

@Service
public class MatchService {
    @Autowired WishRepository wishRepository;
    @Autowired DisposeRepository disposeRepository;
    @Autowired NegotiationRepository negotiationRepository;

    public void procurarMatchesParaUsuario(User user) {
        List<Wish> wishes = user.getWishes();
        for (Wish wish : wishes) {
            List<Dispose> disposes = disposeRepository.findByItemId(wish.getItem().getId());
            for (Dispose dispose : disposes) {
                if (!dispose.getUser().getId().equals(user.getId())) {
                    // Verifica se já existe negociação entre esses dois usuários para esses itens
                    boolean exists = negotiationRepository.findByUsername(user.getUsername()).stream()
                        .anyMatch(n -> 
                            (n.getDisposeA().getUser().getId().equals(user.getId()) && n.getDisposeB().getUser().getId().equals(dispose.getUser().getId()) && n.getDisposeA().getItem().getId().equals(wish.getItem().getId()))
                        );
                    if (!exists) {
                        // Cria negociação
                        Negotiation negotiation = new Negotiation();
                        // DisposeA: quem deseja, DisposeB: quem disponibiliza
                        negotiation.setDisposeA(dispose); 
                        negotiation.setDisposeB(dispose);
                        negotiationRepository.save(negotiation);
                    }
                }
            }
        }
    }
}