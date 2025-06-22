package br.com.escambo.app.control;

import br.com.escambo.app.model.ItemRepository;
import br.com.escambo.app.model.UserRepository;
import br.com.escambo.app.model.WishRepository;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.entities.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class WishControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired WishRepository wishRepository;

    private User user;
    private Item item;

    @BeforeEach
    void setup() {
        user = userRepository.findByUsername("usuarioTeste");
        if (user == null) {
            user = new User();
            user.setUsername("usuarioTeste");
            user.setPassword("senha");
            userRepository.save(user);
        }
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            item = new Item();
            item.setItemname("Livro Teste");
            itemRepository.save(item);
        } else {
            item = items.get(0);
        }
        // Limpa desejos antigos
        wishRepository.deleteAll(wishRepository.findAll().stream()
            .filter(w -> w.getUser().getUsername().equals("usuarioTeste"))
            .toList());
    }

    @Test
    @WithMockUser(username = "usuarioTeste")
    void testAddAndRemoveWish() throws Exception {
        // Adiciona desejo
        mockMvc.perform(post("/wishlist/add")
                .param("itemId", item.getId().toString())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        List<Wish> wishes = wishRepository.findByItemId(item.getId());
        assertThat(wishes.stream().anyMatch(w -> w.getUser().getUsername().equals("usuarioTeste"))).isTrue();

        // Remove desejo
        Wish wish = wishes.stream().filter(w -> w.getUser().getUsername().equals("usuarioTeste")).findFirst().orElse(null);
        assertThat(wish).isNotNull();

        mockMvc.perform(post("/wishlist/remove")
                .param("wishId", wish.getId().toString())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Wish wishAfter = wishRepository.findById(wish.getId()).orElse(null);
        assertThat(wishAfter).isNull();
    }
}