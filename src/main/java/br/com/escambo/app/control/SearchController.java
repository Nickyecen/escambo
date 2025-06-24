package br.com.escambo.app.control;

import java.security.Principal;
import java.util.ArrayList;
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

@Controller public class SearchController {

    @Autowired ItemRepository itemRepository;
    @Autowired DisposeRepository disposeRepository;
    @Autowired NegotiationService negotiationService;
    @Autowired UserRepository userRepository;

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "filter", required = false, defaultValue = "itemname") String filter,
                             @RequestParam(name = "q", required = false) String query,
                             Model model,
                             Principal principal) {
        if(Objects.isNull(query) || query.isBlank()) return "pages/search";

        List<Dispose> disposes = new ArrayList<>();
        List<Dispose> proposerdisposes = disposeRepository.findByUserUsername(principal.getName());

        switch (filter) {
            case "itemname":
                disposes = disposeRepository.findByUserUsernameNotAndItemItemnameContainingIgnoreCase(principal.getName(), query);
                break;
            case "category":
                disposes = disposeRepository.findByUserUsernameNotAndItemCategoryContainingIgnoreCase(principal.getName(), query);
                break;
            case "volume":
                disposes = disposeRepository.findByUserUsernameNotAndItemVolumeContainingIgnoreCase(principal.getName(), query);
                break;
            case "author":
                disposes = disposeRepository.findByUserUsernameNotAndItemAuthorContainingIgnoreCase(principal.getName(), query);
                break;
        }

        model.addAttribute("disposes", disposes);
        model.addAttribute("proposerDisposes", proposerdisposes);

        return "pages/searchresults";
    }

    @PostMapping("/search/propose")
    public String proposeNegotiation(@RequestParam("disposeId") Long disposeId, @RequestParam("proposerDisposeId") Long proposerDisposeId, Principal principal) {
        Dispose targetDispose = disposeRepository.findById(disposeId).orElse(null);
        Dispose proposerDispose = disposeRepository.findById(proposerDisposeId).orElse(null);
        if (targetDispose == null || proposerDispose == null) return "redirect:/search";
        // Garante que o proposerDispose pertence ao usuário logado
        if (!proposerDispose.getUser().getUsername().equals(principal.getName())) return "redirect:/search";
        negotiationService.createManualNegotiation(proposerDispose, targetDispose);
        return "redirect:/negotiations";
    }
}
