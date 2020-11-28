package com.wms.stock;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface StockRepository extends MongoRepository<Stock,String> {
    @Query("{'barcode':?0}")
    List<Stock> getQuantity(String barcode);
}
