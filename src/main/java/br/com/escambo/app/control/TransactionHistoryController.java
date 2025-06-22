package br.com.escambo.app.control;

import br.com.escambo.app.model.TokenTransactionRepository;
import br.com.escambo.app.model.entities.TokenTransaction;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TransactionHistoryController {

    @Autowired TokenTransactionRepository tokenTransactionRepository;
    @Autowired UserRepository userRepository;

    @GetMapping("/transactions")
    public String viewTransactions(Model model, Principal principal) {
        String username = principal.getName();
        List<TokenTransaction> transactions = tokenTransactionRepository
            .findByUserAOrUserB(username, username);
        model.addAttribute("transactions", transactions);
        model.addAttribute("currentUser", username);
        return "pages/transactions";
    }
}