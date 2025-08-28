package com.Malik.RentO.Repository;

import com.Malik.RentO.Entity.Booking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    List<Booking> findByRenterId(String renterId);
    List<Booking> findByItemId(String itemId);
}
