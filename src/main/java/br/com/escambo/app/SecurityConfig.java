package br.com.escambo.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()  // allow login and static files
                .anyRequest().authenticated() // everything else needs auth
            )
            .formLogin(form -> form
                .loginPage("/login")        // your custom login page
                .defaultSuccessUrl("/", true) // where to go on success
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // redirect after logout
                .permitAll()
            );

        return http.build();
    }

}
