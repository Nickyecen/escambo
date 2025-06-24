package br.com.escambo.app.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import br.com.escambo.app.model.UserService;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.ItemService;
import org.springframework.security.crypto.password.PasswordEncoder;



@Controller
public class RegisterAccountController {

    @Autowired UserService userService;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ItemService itemService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "pages/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Já há usuário cadastrado com esse nome :(");
            return "pages/register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userService.save(user);
        return "redirect:/login";
    }
}
