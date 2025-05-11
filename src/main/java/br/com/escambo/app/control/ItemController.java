package br.com.escambo.app.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.ItemRepository;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired ItemRepository itemRepository;

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam("q") String query) {
        return itemRepository.findByItemnameContainingIgnoreCase(query);
    }
}
