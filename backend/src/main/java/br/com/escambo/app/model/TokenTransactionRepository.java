package br.com.escambo.app.model;

import br.com.escambo.app.model.entities.TokenTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenTransactionRepository extends JpaRepository<TokenTransaction, Long> {
}