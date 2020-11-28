package com.wms.addBarcode;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AddBarcodeRepository extends MongoRepository<AddBarcode,String> {
    @Query("{'barcode':?0}")
    List<AddBarcode> getBarcodeData(String barcode);
}
