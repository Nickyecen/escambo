package br.com.escambo.app.model;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.entities.User;
import br.com.escambo.app.model.entities.Interaction;
import java.util.Collections;
import br.com.escambo.app.model.InteractionRepository;
import br.com.escambo.app.model.entities.Item;
@Service
public class ModService {

    @Autowired  UserService userService;
    @Autowired  ItemService itemService;
    @Autowired  InteractionService interactionService;

    @Autowired
    private InteractionRepository interactionRepository;

    @Transactional
    public boolean banUserByUsername(Long modId, String username) {
        User userBan = userService.findByUsername(username);
        if (userBan == null) {
            return false;
        }
        //Moderador nao bane outreo moderador, isso faz o administrador
        if(userBan.getRole().equals("ROLE_MOD")) {
            return false;
        }
        // Ou ver se o moderador é válido, caso um usuario comum tenha acesso a /mods
        if (userBan.getBanned()) {
            return false; // Usuario ja esta banido
        }

        userBan.setBanned(true);
        userService.save(userBan);
        interactionService.createInteraction("ban", userBan.getId(), modId);
        return true;
    }

    @Transactional
    public boolean unbanUserByUsername(Long modId, String username) {
        User userUnban = userService.findByUsername(username);
        if (userUnban == null) {
            return false;
        }
        //Moderador nao desbane outro moderador, isso faz o administrador
        if(userUnban.getRole().equals("ROLE_MOD")) {
            return false;
        }
        userUnban.setBanned(false);
        userService.save(userUnban);
        interactionService.createInteraction("unban", userUnban.getId(), modId);
        return true;
    }
    @Transactional
    public boolean approveItem(Long modId, String itemName, boolean approval) {
        ItemRequest req = itemService.findItemRequestByName(itemName);
        if (req == null) {
            return false; // Nao foi encontrado o pedido de item
        }
        if(approval){
            try{
                itemService.createItemFromRequest(req);
            }
            catch (IllegalArgumentException e) {
                return false;
            }
        }
        itemService.deleteItemRequest(req);
        return true; //Deletou o pedido de item e criou o item se aprovado

    }

    @Transactional
    public boolean removeItem(Long modId, String itemName) {
        Item item = itemService.findItemByName(itemName);
        if (item == null) {
            return false; // Item não encontrado
        }
        itemService.deleteItem(item);
        return true;
    }

    public List<Interaction> getBanHistory(Long modId, Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado com id: " + userId);
        }
        List<Interaction> interactions = interactionService.findInteractionByTargetUsername(user.getUsername());
        if (interactions.isEmpty()) {
            throw new RuntimeException("Nada encontrado no historico do usuário: " + user.getUsername());
        }
        return interactions;
    }

   public List<Interaction> getAllBanLogs() {
    return interactionRepository.findByTypeIn(List.of("ban", "unban"));
    }

    public List<ItemRequest> getAllItemRequests() {
        return itemService.getItemRequests();
    }

    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
