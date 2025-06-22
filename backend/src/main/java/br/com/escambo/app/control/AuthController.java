package br.com.escambo.app.control;

import br.com.escambo.app.control.dto.LoginRequest;
import br.com.escambo.app.control.dto.RegisterRequest;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import br.com.escambo.app.config.JwtUtil;
import br.com.escambo.app.control.dto.TokenResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtUtil.generate(authentication.getName());

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, "access_token=" + token + "; HttpOnly; Path=/")
            .body(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já está em uso");
        }

        User user = new User();
        user.setUsername(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getSenha()));

        userRepository.save(user);

        String token = JwtUtil.generate(user.getUsername());

        return ResponseEntity.status(201).body(new TokenResponse(token));
    }
}
