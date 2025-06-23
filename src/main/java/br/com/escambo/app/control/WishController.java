package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

import br.com.escambo.app.model.WishRepository;
import br.com.escambo.app.model.dto.ItemDTO;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.Wish;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.MatchService;
import br.com.escambo.app.model.ItemRequestRepository;

@Controller
@RequestMapping("/wishlist")
public class WishController {
    @Autowired WishRepository wishRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired MatchService matchService;
    @Autowired ItemRequestRepository itemRequestRepository;

    @GetMapping
    public String listWishes(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Wish> wishes = user.getWishes();
        model.addAttribute("wishes", wishes);
        model.addAttribute("allItems", itemRepository.findAll());
        return "pages/wishlist";
    }

    @PostMapping("/add")
    public String addWish(ItemDTO itemDTO, Principal principal, Model model) {
    User user = userRepository.findByUsername(principal.getName());
        Item item = itemRepository.findByItemname(itemDTO.getItemname());
        if (item == null) {
            // Item não existe, cria pedido de aprovação
            ItemRequest req = new ItemRequest();
            req.setItemname(itemDTO.getItemname());
            req.setRequestedBy(user.getUsername());
            req.setCategory(itemDTO.getCategory());
            req.setVolume(itemDTO.getVolume());
            req.setAuthor(itemDTO.getAuthor());

            itemRequestRepository.save(req);
            model.addAttribute("error", "Item enviado para aprovação.");
            return "redirect:/wishlist";
        }
        boolean exists = wishRepository.findByItemId(item.getId())
            .stream().anyMatch(w -> w.getUser().getId().equals(user.getId()));
        if (!exists) {
            Wish wish = new Wish();
            wish.setUser(user);
            wish.setItem(item);
            wishRepository.save(wish);
            matchService.procurarMatchesParaUsuario(user);
        }
        return "redirect:/wishlist";
    }   

    @PostMapping("/remove")
    public String removeWish(@RequestParam("wishId") Long wishId, Principal principal) {
        Wish wish = wishRepository.findById(wishId).orElse(null);
        if (wish != null && wish.getUser().getUsername().equals(principal.getName())) {
            wishRepository.delete(wish);
        }
        return "redirect:/wishlist";
    }
}