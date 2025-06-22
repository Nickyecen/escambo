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
    public String showRegisterForm() {
        return "pages/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        //username é unique, entao cancela o registro
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Já há usuário cadastrado com esse nome :(");
            return "pages/register";
        }
        //cria usuario novo
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USR");
        userService.save(user);
        return "redirect:/login";
    }
}