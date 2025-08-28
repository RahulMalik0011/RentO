package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Repository.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void addItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> getAllItem(){
        return itemRepository.findAll();
    }
    public List<Item> findItemByTitle(String title){
        return itemRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<Item> getItemByCategory(String category){
        return  itemRepository.findByCategory(category);
    }

    public List<Item> getAvailableItems() {
        return itemRepository.findByAvailableTrue();
    }
    public void updateItemAvailability(ObjectId itemId, boolean available) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setAvailable(available);
        itemRepository.save(item);
    }
}
