package com.wms.threshold;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ThresholdRepository extends MongoRepository<Threshold,Long> {

@Query("{'barcode':?0}")
List<Threshold> getThreshold(String barcode);



}
