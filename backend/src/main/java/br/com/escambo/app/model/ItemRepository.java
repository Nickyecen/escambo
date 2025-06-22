package br.com.escambo.app.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemname(String itemname);

    List<Item> findByItemnameContainingIgnoreCase(String query);
}
