package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

import br.com.escambo.app.model.WishRepository;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.Wish;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.MatchService;

@Controller
@RequestMapping("/wishlist")
public class WishController {
    @Autowired WishRepository wishRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired MatchService matchService;

    @GetMapping
    public String listWishes(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Wish> wishes = user.getWishes();
        model.addAttribute("wishes", wishes);
        model.addAttribute("allItems", itemRepository.findAll());
        return "pages/wishlist";
    }

    @PostMapping("/add")
public String addWish(@RequestParam Long itemId, Principal principal, Model model) {
    User user = userRepository.findByUsername(principal.getName());
    Item item = itemRepository.findById(itemId).orElse(null);
    if (item == null) {
        model.addAttribute("error", "Item não aprovado.");
        return "redirect:/wishlist";
    }
    boolean exists = wishRepository.findByItemId(itemId)
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
    public String removeWish(@RequestParam Long wishId, Principal principal) {
        Wish wish = wishRepository.findById(wishId).orElse(null);
        if (wish != null && wish.getUser().getUsername().equals(principal.getName())) {
            wishRepository.delete(wish);
        }
        return "redirect:/wishlist";
    }
}