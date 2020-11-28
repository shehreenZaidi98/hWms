package com.wms.today;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TodayPurchaseRepository extends MongoRepository<TodayPurchase,String> {

    @Query("{'date':?0}")
    List<TodayPurchase> getTodayData(String date);
}
