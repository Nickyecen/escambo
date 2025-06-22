package br.com.escambo.app.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller public class LoginController {

    @GetMapping("/login") public String showLoginForm() {
        return "pages/login";
    }
    
}
