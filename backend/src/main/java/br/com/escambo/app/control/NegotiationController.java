package br.com.escambo.app.control;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.escambo.app.model.NegotiationRepository;
import br.com.escambo.app.model.NegotiationService;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.Negotiation;
import br.com.escambo.app.model.entities.User;

@RequestMapping("/negotiations")
@Controller public class NegotiationController {
    @Autowired NegotiationRepository negotiationRepository;
    @Autowired UserRepository userRepository;

    @Autowired NegotiationService negotiationService;

    @GetMapping public String viewNegotiations(Model model, Principal principal) {
        List<Negotiation> negotiations = negotiationRepository.findByUsername(principal.getName());
        User currentUser = userRepository.findByUsername(principal.getName());

        model.addAttribute("negotiations", negotiations);
        model.addAttribute("currentUser", currentUser);

        return "pages/negotiations";
    }

    @PostMapping("/confirm") public String confirmNegotation(@RequestParam("negotiationId") Long negotiationId, Principal principal) {
        negotiationService.confirmNegotiation(negotiationId, principal.getName());

        return "redirect:/negotiations";
    }
}
