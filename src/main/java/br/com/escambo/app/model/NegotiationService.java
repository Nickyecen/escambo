package br.com.escambo.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escambo.app.model.entities.Negotiation;

@Service public class NegotiationService {
    @Autowired NegotiationRepository negotiationRepository;
    @Autowired UserRepository userRepository;
    @Autowired DisposeRepository disposeRepository;

    public void confirmNegotiation(Long negotiationId, String username) {
        Negotiation negotiation = negotiationRepository.getReferenceById(negotiationId);

        if(negotiation.getDisposeA().getUser().getUsername().equals(username)) {
            negotiation.setUserAOk(true);
        } else if(negotiation.getDisposeB().getUser().getUsername().equals(username)) {
            negotiation.setUserBOk(true);
        } 

        if(negotiation.isUserAOk() && negotiation.isUserBOk()) {
            disposeRepository.delete(negotiation.getDisposeA());
            disposeRepository.delete(negotiation.getDisposeB());
            negotiationRepository.delete(negotiation);
        } else {
            negotiationRepository.save(negotiation);
        }

    }
}
