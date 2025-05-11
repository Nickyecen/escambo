package br.com.escambo.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;

@Configuration public class DataInitializer {

    @Bean public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRole("ROLE_USER");
                userRepository.save(user);
            }

            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
            }

        };
    }

    @Bean public CommandLineRunner initItems(ItemRepository itemRepository) {
        return args -> {

            if (itemRepository.findByItemname("Book") == null) {
                Item item = new Item();
                item.setItemname("Book");
                itemRepository.save(item);
            }

        };
    }
}
