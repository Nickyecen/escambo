package br.com.escambo.app.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.escambo.app.model.entities.Item;
import br.com.escambo.app.model.entities.ItemRequest;

@Service
public class ItemService {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemRequestRepository itemRequestRepository;

    public Item createItem(String itemname) {
        if (itemRepository.findByItemname(itemname) != null)
            throw new IllegalArgumentException("Item já existe.");
        Item item = new Item();
        item.setItemname(itemname);
        return itemRepository.save(item);
    }
    public Item createItem(String itemname, String category, String volume, String author) {
        if (itemRepository.findByItemname(itemname) != null)
            throw new IllegalArgumentException("Item já existe.");
        Item item = new Item();
        item.setItemname(itemname);
        item.setCategory(category);
        item.setVolume(volume);
        item.setAuthor(author);
        return itemRepository.save(item);
    }
    public Item findItemByName(String itemname) {
        Item item = itemRepository.findByItemname(itemname);
        if (item == null)
            throw new IllegalArgumentException("Não deu para encontrar o item: " + itemname);
        return item;
    }

    public String requestItem(String itemname, String username) {
        Item item = itemRepository.findByItemname(itemname);
        if (item == null) {
            // Verifica se o item já foi solicitado
            if (itemRequestRepository.findByItemname(itemname) != null) {
                return "Item já solicitado.";
            }
            // Cria um novo pedido de item
            ItemRequest itemRequest = new ItemRequest();
            itemRequest.setItemname(itemname);
            itemRequest.setRequestedBy(username);
            itemRequestRepository.save(itemRequest);
            return "Pedido de item criado com sucesso, boa sorte.";
        }
        return "Item já existe.";
    }
    public boolean deleteItemRequest(ItemRequest itemRequest) {
        if (itemRequest == null) {
            return false; // Item request not found
        }
        itemRequestRepository.delete(itemRequest);
        return true; // Item request deleted successfully
    }
    public ItemRequest findItemRequestByName(String itemname) {
        ItemRequest itemRequest = itemRequestRepository.findByItemname(itemname);
        if (itemRequest == null) {
            throw new IllegalArgumentException("Pedido de item não encontrado: " + itemname);
        }
        return itemRequest;
    }

    public List<ItemRequest> getItemRequests() {
        return itemRequestRepository.findAll();
    }

    public List<Item> getAllItems() {
    return itemRepository.findAll();
    }

    public Item createItemFromRequest(ItemRequest req) {
        if (itemRepository.findByItemname(req.getItemname()) != null)
            throw new IllegalArgumentException("Item já existe.");

        Item item = new Item();
        item.setItemname(req.getItemname());

        if (req.getCategory() != null) {
            item.setCategory(req.getCategory());
        }
        if (req.getVolume() != null) {
            item.setVolume(req.getVolume());
        }
        if (req.getAuthor() != null) {
            item.setAuthor(req.getAuthor());
        }

        return itemRepository.save(item);
    }

    public boolean deleteItem(Item item) {
        if (item == null) {
            return false;
        }
        itemRepository.delete(item);
        return true;
    }
}
