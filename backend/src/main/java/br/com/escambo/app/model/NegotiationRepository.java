package br.com.escambo.app.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escambo.app.model.entities.Negotiation;

public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {
    @Query("SELECT n FROM Negotiation n WHERE n.disposeA.user.username = :username OR n.disposeB.user.username = :username") 
    List<Negotiation> findByUsername(@Param("username") String username);
}
