package com.Malik.RentO.Repository;

import com.Malik.RentO.Entity.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, ObjectId> {
    List<Item> findByCategory(String category);
    List<Item> findByTitleContainingIgnoreCase(String title);  // Search by name
    List<Item> findByAvailableTrue();
}
