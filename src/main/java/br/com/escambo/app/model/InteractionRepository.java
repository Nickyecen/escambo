package br.com.escambo.app.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.Interaction;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByTargetUsername(String targetUsername);

}
