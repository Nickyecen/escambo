package br.com.escambo.app.model;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.escambo.app.model.entities.ItemRequest;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    ItemRequest findByItemname(String itemRequestName);
}