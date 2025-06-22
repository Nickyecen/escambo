package br.com.escambo.app.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.ItemRequest;
import br.com.escambo.app.model.entities.User;

@Service
public class ModService {

    private final UserService userService;
    private final ItemService itemService;

    public ModService(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }
    @Transactional
    public boolean banUserByUsername(Long modId, String username) {
        User userBan = userService.findByUsername(username);
        if (userBan == null) {
            return false;
        }
        //Moderador nao bane outreo moderador, isso faz o administrador
        if(userBan.getRole().equals("MOD")) {
            return false; 
        }
        // registrar o ID do moderador que fez o banimento, se necessário
        // Ou ver se o moderador é válido, caso um usuario comum tenha acesso a /mods
        userBan.setBanned(true);
        userService.save(userBan);
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
                itemService.createItem(itemName);
            }
            catch (IllegalArgumentException e) {
                return false;
            }
        }
        itemService.deleteItemRequest(req);
        return true; //Deletou o pedido de item e criou o item se aprovado

    }

}

