package br.com.escambo.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.escambo.app.model.entities.Dispose;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.entities.Negotiation;
import br.com.escambo.app.model.entities.Notification;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.entities.Wish;
import br.com.escambo.app.model.DisposeRepository;
import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.ItemRequestRepository;
import br.com.escambo.app.model.NegotiationRepository;
import br.com.escambo.app.model.NotificationRepository;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.WishRepository;

@Configuration public class DataInitializer {

    @Bean public CommandLineRunner initData(UserRepository userRepository,
                                            PasswordEncoder passwordEncoder,
                                            ItemRepository itemRepository,
                                            WishRepository wishRepository,
                                            DisposeRepository disposeRepository,
                                            NotificationRepository notificationRepository,
                                            NegotiationRepository negotiationRepository,
                                            ItemRequestRepository itemRequestRepository) {
        return args -> {

            User user1 = new User();
            user1.setUsername("leo");
            user1.setPassword(passwordEncoder.encode("password"));
            user1.setRole("ROLE_USER");
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("joao");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setRole("ROLE_USER");
            userRepository.save(user2);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);

            User mod = new User();
            mod.setUsername("mod");
            mod.setPassword(passwordEncoder.encode("mod123"));
            mod.setRole("ROLE_MOD");
            userRepository.save(mod);

            ItemRequest req1 = new ItemRequest();
            req1.setItemname("Senhor dos Anéis");
            req1.setCategory("Literatura");
            req1.setVolume("Volume 1");
            req1.setAuthor("J.R.R. Tolkien");
            req1.setRequestedBy(user1.getUsername());
            itemRequestRepository.save(req1);

            ItemRequest req2 = new ItemRequest();
            req2.setItemname("A Revolução dos Bichos");
            req2.setCategory("Literatura");
            req2.setVolume("Único");
            req2.setAuthor("George Orwell");
            req2.setRequestedBy(user2.getUsername());
            itemRequestRepository.save(req2);

            Item itemA = new Item();
            itemA.setItemname("CD Capital Inicial - Acústico");
            itemA.setCategory("Música");
            itemA.setVolume("Volume único");
            itemA.setAuthor("Capital Inicial");
            itemRepository.save(itemA);

            Item itemB = new Item();
            itemB.setItemname("Livro Mafalda");
            itemB.setCategory("Quadrinhos");
            itemB.setVolume("Volume único");
            itemB.setAuthor("Quino");
            itemRepository.save(itemB);

            Item itemC = new Item();
            itemC.setItemname("Gibi Turma da Mônica");
            itemC.setCategory("Quadrinhos");
            itemC.setVolume("Volume único");
            itemC.setAuthor("Maurício de Sousa");
            itemRepository.save(itemC);

            Item itemD = new Item();
            itemD.setItemname("CD Van Halen");
            itemD.setCategory("Música");
            itemD.setVolume("Volume único");
            itemD.setAuthor("Van Halen");
            itemRepository.save(itemD);

            Item itemE = new Item();
            itemE.setItemname("Caneta Bic");
            itemE.setCategory("Material Escolar");
            itemE.setVolume("Padrão");
            itemE.setAuthor("BIC");
            itemRepository.save(itemE);

            Item item1 = itemRepository.findByItemname("CD Capital Inicial - Acústico");
            Item item2 = itemRepository.findByItemname("Livro Mafalda");
            Item item3 = itemRepository.findByItemname("Gibi Turma da Mônica");
            Item item4 = itemRepository.findByItemname("CD Van Halen");

            Dispose dispose1 = new Dispose();
            dispose1.setItem(item1);
            dispose1.setUser(user1);
            disposeRepository.save(dispose1);

            Dispose dispose2 = new Dispose();
            dispose2.setItem(item2);
            dispose2.setUser(user1);
            disposeRepository.save(dispose2);

            Dispose dispose3 = new Dispose();
            dispose3.setItem(item3);
            dispose3.setUser(user2);
            disposeRepository.save(dispose3);

            Dispose dispose4 = new Dispose();
            dispose4.setItem(item4);
            dispose4.setUser(user2);
            disposeRepository.save(dispose4);

            Negotiation negotiation1 = new Negotiation();
            negotiation1.setDisposeA(dispose1);
            negotiation1.setDisposeB(dispose3);
            negotiationRepository.save(negotiation1);

            Negotiation negotiation2 = new Negotiation();
            negotiation2.setDisposeA(dispose2);
            negotiation2.setDisposeB(dispose4);
            negotiationRepository.save(negotiation2);

            Wish wish1 = new Wish();
            wish1.setUser(user1);
            wish1.setItem(item3);
            wishRepository.save(wish1);

            Wish wish2 = new Wish();
            wish2.setUser(user1);
            wish2.setItem(item4);
            wishRepository.save(wish2);

            Wish wish3 = new Wish();
            wish3.setUser(user2);
            wish3.setItem(item1);
            wishRepository.save(wish3);

            Wish wish4 = new Wish();
            wish4.setUser(user2);
            wish4.setItem(item2);
            wishRepository.save(wish4);

            Notification not1 = new Notification();
            not1.setUsername(user1.getUsername());
            not1.setMessage("Nova notificação");
            not1.setTimestamp(LocalDateTime.now());
            notificationRepository.save(not1);

            Notification not2 = new Notification();
            not2.setUsername(user2.getUsername());
            not2.setMessage("Nova proposta");
            not2.setTimestamp(LocalDateTime.now());
            notificationRepository.save(not2);
        };
    }
}
