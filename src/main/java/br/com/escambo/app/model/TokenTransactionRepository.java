package br.com.escambo.app.model;

import br.com.escambo.app.model.entities.TokenTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TokenTransactionRepository extends JpaRepository<TokenTransaction, Long> {
    List<TokenTransaction> findByUserAOrUserB(String userA, String userB);
}