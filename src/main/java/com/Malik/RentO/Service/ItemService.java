package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Repository.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;

    public void addItem(Item item, String email){
        Optional<User> userE = userService.getUserByEmail(email);
        if (userE.isPresent()){
            User user = userE.get();
            ObjectId userId = user.getId();
            item.setOwnerId(userId);
            Item save = itemRepository.save(item);
            ObjectId id = save.getId();
            user.getItemId().add(id);
            userService.saveUser(user);
        }

    }
    public List<Item> getItemsByIds(List<ObjectId> ids){
        return itemRepository.findAllById(ids);
    }
    public List<Item> findItemByTitle(String title){
        return itemRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<Item> getItemByCategory(String category){
        return  itemRepository.findByCategory(category);
    }
    public List<Item> getAvailableItemsExcludingOwner(ObjectId ownerId) {
        return itemRepository.findByAvailableTrueAndOwnerIdNot(ownerId);
    }

    public List<Item> getAvailableItems() {
        return itemRepository.findByAvailableTrue();
    }
    public void updateItemAvailability(ObjectId itemId, boolean available) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setAvailable(available);
        itemRepository.save(item);
    }

    public Optional<Item> getItemById(ObjectId objectId) {
        return itemRepository.findById(objectId);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
    public boolean deleteItemById(ObjectId id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
