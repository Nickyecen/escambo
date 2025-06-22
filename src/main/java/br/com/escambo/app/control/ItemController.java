package br.com.escambo.app.control;

import java.util.List;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.ItemRequestRepository;
import br.com.escambo.app.model.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam("q") String query) {
        return itemRepository.findByItemnameContainingIgnoreCase(query);
    }
    @PostMapping("/request")
    public String requestItem(@RequestParam String itemname, Principal principal) {
        return itemService.requestItem(itemname, principal.getName());
    }

}
