package br.com.escambo.app.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.dto.ItemDTO;
import br.com.escambo.app.model.entities.Item;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired ItemRepository itemRepository;

    @GetMapping
    public List<ItemDTO> list(@RequestParam(name = "q", required = false) String query) {
        List<Item> items = (query == null || query.isBlank())
            ? itemRepository.findAll()
            : itemRepository.findByItemnameContainingIgnoreCase(query);

        return items.stream().map(ItemDTO::from).toList();
    }
}
