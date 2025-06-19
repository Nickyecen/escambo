package br.com.escambo.app.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escambo.app.model.entities.Negotiation;
import br.com.escambo.app.model.entities.TokenTransaction;

@Service public class NegotiationService {
    @Autowired NegotiationRepository negotiationRepository;
    @Autowired UserRepository userRepository;
    @Autowired DisposeRepository disposeRepository;
    @Autowired private TokenTransactionRepository tokenTransactionRepository;

    public void confirmNegotiation(Long negotiationId, String username) {
        Negotiation negotiation = negotiationRepository.getReferenceById(negotiationId);

        if(negotiation.getDisposeA().getUser().getUsername().equals(username)) {
            negotiation.setUserAOk(true);
        } else if(negotiation.getDisposeB().getUser().getUsername().equals(username)) {
            negotiation.setUserBOk(true);
        } 

        if(negotiation.isUserAOk() && negotiation.isUserBOk()) {
            efetivarTroca(negotiation);
            disposeRepository.delete(negotiation.getDisposeA());
            disposeRepository.delete(negotiation.getDisposeB());
            negotiationRepository.delete(negotiation);
        } else {

            negotiationRepository.save(negotiation);
        }

    }
    private void efetivarTroca(Negotiation negotiation) {
        TokenTransaction tx = new TokenTransaction();
        tx.setItemAId(negotiation.getDisposeA().getItem().getId());
        tx.setItemBId(negotiation.getDisposeB().getItem().getId());
        tx.setUserA(negotiation.getDisposeA().getUser().getUsername());
        tx.setUserB(negotiation.getDisposeB().getUser().getUsername());
        tx.setTransactionDate(LocalDateTime.now());
        tokenTransactionRepository.save(tx);    
    }
}
