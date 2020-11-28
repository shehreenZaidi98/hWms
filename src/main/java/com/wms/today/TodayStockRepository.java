package com.wms.today;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodayStockRepository extends MongoRepository<TodayStock,String> {

    @Query("{'date':?0}")
    List<TodayStock> getTodayData(String date);

}
