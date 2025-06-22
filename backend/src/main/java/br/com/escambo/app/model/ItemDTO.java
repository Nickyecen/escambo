package br.com.escambo.app.dto;

import br.com.escambo.app.model.entities.Item;

public record ItemDTO(Long id, String itemname) {
    public static ItemDTO from(Item item) {
        return new ItemDTO(item.getId(), item.getItemname());
    }
}
