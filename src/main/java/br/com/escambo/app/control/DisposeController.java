package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

import br.com.escambo.app.model.DisposeRepository;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.MatchService;

@Controller
@RequestMapping("/disposelist")
public class DisposeController {
    @Autowired DisposeRepository disposeRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired MatchService matchService;

    @GetMapping
    public String listDispos(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Dispose> disposes = user.getDisposetions();
        model.addAttribute("disposes", disposes);
        model.addAttribute("allItems", itemRepository.findAll());
        return "pages/disposelist";
    }

    @PostMapping("/add")
    public String addDispose(@RequestParam Long itemId, Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            model.addAttribute("error", "Item não aprovado.");
            return "redirect:/disposelist";
        }
        boolean exists = disposeRepository.findByItemId(itemId)
            .stream().anyMatch(d -> d.getUser().getId().equals(user.getId()));
        if (!exists) {
            Dispose dispose = new Dispose();
            dispose.setUser(user);
            dispose.setItem(item);
            disposeRepository.save(dispose);
            // Chama o match automático
            matchService.procurarMatchesParaUsuario(user);
        }
        return "redirect:/disposelist";
    }

    @PostMapping("/remove")
    public String removeDispose(@RequestParam("disposeId") Long disposeId, Principal principal) {
        Dispose dispose = disposeRepository.findById(disposeId).orElse(null);
        if (dispose != null && dispose.getUser().getUsername().equals(principal.getName())) {
            disposeRepository.delete(dispose);
        }
        return "redirect:/disposelist";
    }
}