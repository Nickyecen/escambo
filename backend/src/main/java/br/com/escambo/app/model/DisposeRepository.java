package br.com.escambo.app.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.Dispose;

public interface DisposeRepository extends JpaRepository<Dispose, Long> {
    List<Dispose> findByItemId(Long itemId);
}
