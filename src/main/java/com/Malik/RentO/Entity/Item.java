package com.Malik.RentO.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "item")
public class Item {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String description;
    @NonNull
    private Double pricePerDay;
    private String category;
    private String imageUrl;
    private Double rating = 0.0;
    private boolean available = true;
     @NonNull
    private ObjectId ownerId;
}
