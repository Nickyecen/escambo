package br.com.escambo.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.DisposeRepository;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;

@Configuration public class DataInitializer {

    @Bean public CommandLineRunner initData(UserRepository userRepository,
                                            PasswordEncoder passwordEncoder,
                                            ItemRepository itemRepository,
                                            DisposeRepository disposeRepository) {
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

            List<String> items = new ArrayList<>();
            items.add("Book"); items.add("Notebook");

            for(String itemname : items) {
                if (itemRepository.findByItemname(itemname) == null) {
                    Item item = new Item();
                    item.setItemname(itemname);
                    itemRepository.save(item);
                }
            } 

            User user1 = userRepository.findByUsername("user");
            User user2 = userRepository.findByUsername("admin");
            Item item1 = itemRepository.findByItemname("Book");
            Item item2 = itemRepository.findByItemname("Notebook");

            Dispose dispose = new Dispose();
            dispose.setItem(item1);
            dispose.setUser(user1);
            disposeRepository.save(dispose);

            dispose = new Dispose();
            dispose.setItem(item2);
            dispose.setUser(user1);
            disposeRepository.save(dispose);

            dispose = new Dispose();
            dispose.setItem(item1);
            dispose.setUser(user2);
            disposeRepository.save(dispose);
        };
    }
}
