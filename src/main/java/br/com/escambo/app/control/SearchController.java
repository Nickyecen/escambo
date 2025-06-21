package br.com.escambo.app.control;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.escambo.app.model.DisposeRepository;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.NegotiationService;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;

@Controller public class SearchController {

    @Autowired ItemRepository itemRepository;
    @Autowired DisposeRepository disposeRepository;
    @Autowired NegotiationService negotiationService;
    @Autowired UserRepository userRepository;

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "q", required = false) String query, Model model) {
        if(Objects.isNull(query) || query.isBlank()) return "pages/search";

        Item item = itemRepository.findByItemname(query);
        List<Dispose> disposes = disposeRepository.findByItemId(item.getId());
        List<String> usernames = disposes.stream().map(Dispose::getUser).map(User::getUsername).toList();

        model.addAttribute("itemname", item.getItemname());
        model.addAttribute("results", usernames);

        return "pages/searchresults";
    }

    @PostMapping("/search/propose")
    public String proposeNegotiation(@RequestParam Long disposeId, @RequestParam Long proposerDisposeId, Principal principal) {
        Dispose targetDispose = disposeRepository.findById(disposeId).orElse(null);
        Dispose proposerDispose = disposeRepository.findById(proposerDisposeId).orElse(null);
        if (targetDispose == null || proposerDispose == null) return "redirect:/search";
        // Garante que o proposerDispose pertence ao usuário logado
        if (!proposerDispose.getUser().getUsername().equals(principal.getName())) return "redirect:/search";
        negotiationService.createManualNegotiation(proposerDispose, targetDispose);
        return "redirect:/negotiations";
    }
}
