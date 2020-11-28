package com.wms.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    @Query("{'date':?0}")
    List<Order> getDate(String date);

}
