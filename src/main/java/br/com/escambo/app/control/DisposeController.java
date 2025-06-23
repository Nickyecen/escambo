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
import br.com.escambo.app.model.dto.ItemDTO;
import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.MatchService;
import br.com.escambo.app.model.ItemRequestRepository;

@Controller
@RequestMapping("/disposelist")
public class DisposeController {
    @Autowired DisposeRepository disposeRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired MatchService matchService;
    @Autowired ItemRequestRepository itemRequestRepository;

    @GetMapping
    public String listDispos(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Dispose> disposes = user.getDisposetions();
        model.addAttribute("disposes", disposes);
        model.addAttribute("allItems", itemRepository.findAll());
        model.addAttribute("itemDTO", new ItemDTO());
        return "pages/disposelist";
    }

    @PostMapping("/add")
    public String addDispose(ItemDTO itemDTO, Principal principal, Model model) {
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
            return "redirect:/disposelist";
        }
        boolean exists = disposeRepository.findByItemId(item.getId())
            .stream().anyMatch(d -> d.getUser().getId().equals(user.getId()));
        if (!exists) {
            Dispose dispose = new Dispose();
            dispose.setUser(user);
            dispose.setItem(item);
            disposeRepository.save(dispose);
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