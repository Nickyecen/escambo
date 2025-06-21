package br.com.escambo.app.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.escambo.app.model.entities.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByItemId(Long itemId);
}