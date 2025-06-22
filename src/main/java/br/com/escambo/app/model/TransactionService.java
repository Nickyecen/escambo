package br.com.escambo.app.service;

import br.com.escambo.app.model.TokenTransactionRepository;
import br.com.escambo.app.model.entities.TokenTransaction;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.DisposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired TokenTransactionRepository tokenTransactionRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired DisposeRepository disposeRepository;
    @Autowired UserService userService;

    public void cancelTransaction(Long transactionId, String username) {
        TokenTransaction tx = tokenTransactionRepository.findById(transactionId).orElse(null);
        if (tx != null && ((tx.getUserA().equals(username) != null) || (tx.getUserB().equals(username) != null))) {
            // Restore itemA to userA
            Item itemA = itemRepository.findById(tx.getItemAId()).orElse(null);
            User userA = userService.findByUsername(tx.getUserA());
            if (itemA != null && userA != null) {
                Dispose disposeA = disposeRepository.findByItemId(itemA.getId())
                    .stream().filter(usercol -> usercol.getUser().getUsername().equals(tx.getUserB())).findFirst().orElse(null);
                if (disposeA != null) {
                    disposeA.setUser(userA);
                    disposeRepository.save(disposeA);
                }
            }
            // Restore itemB to userB
            Item itemB = itemRepository.findById(tx.getItemBId()).orElse(null);
            User userB = userService.findByUsername(tx.getUserB());
            if (itemB != null && userB != null) {
                Dispose disposeB = disposeRepository.findByItemId(itemB.getId())
                    .stream().filter(usercol -> usercol.getUser().getUsername().equals(tx.getUserA())).findFirst().orElse(null);
                if (disposeB != null) {
                    disposeB.setUser(userB);
                    disposeRepository.save(disposeB);
                }
            }
            tokenTransactionRepository.deleteById(transactionId);
        }
    }
}