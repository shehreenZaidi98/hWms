package com.wms.threshold;

import com.wms.stock.Stock;
import com.wms.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ThresholdController {
@Autowired
    ThresholdRepository thresholdRepository;
@Autowired
    MongoTemplate mongoTemplate;
@Autowired
    StockRepository stockRepository;
@PostMapping("insertThreshold")
    public String insertThreshold(@RequestBody Threshold threshold){
   String message="Not inserted";
   List<Threshold>list=thresholdRepository.getThreshold(threshold.getBarcode());
    if(list.size()>0){
        Query query=new Query();
        query.addCriteria(Criteria.where("barcode").is(list.get(0).getBarcode()));
        Update update = new Update();
        update.set("threshold_value",threshold.getThreshold_value());
        mongoTemplate.upsert(query,update,Threshold.class);
        if(list.size()>0){
            message="Updated";
        }
    }else{
        Threshold threshold1=thresholdRepository.save(threshold);
    if(!threshold1.getId().isEmpty()) {
        message = "insert";
    }
    }


    return message;
}

    @GetMapping("getThreshold")
    public Map<String,ArrayList<Stock>> getThreshold(){
    List<Threshold> list=thresholdRepository.findAll();
    String message="Available";
        ArrayList<Stock>stockList=new ArrayList<>();
    for(Threshold threshold1:list) {
        List<Stock>list1=stockRepository.getQuantity(threshold1.getBarcode());
        int quantity=0;
        String product_name="";
        int size = 0;
    for(Stock stock:list1){
        quantity+=stock.getQuantity();
        product_name=stock.getProduct_name();
        size=stock.getSize();
    }
    if(quantity<Integer.parseInt(threshold1.getThreshold_value())){
      Stock stock=new Stock(threshold1.getBarcode(),product_name,quantity,size);
      stockList.add(stock);

    }
    }
        HashMap<String,ArrayList<Stock>>hMap=new HashMap<>();
    hMap.put("value",stockList);

    return hMap;
    }
}

