package com.Malik.RentO.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking")
public class Booking {
    @Id
    private ObjectId id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalPrice;
    private String status;  // PENDING, CONFIRMED, CANCELLED

    private ObjectId itemId;   // Reference to Item
    private ObjectId renterId;
    private ObjectId ownerId;
}
;