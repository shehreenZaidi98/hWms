package com.wms.stock;

import com.wms.today.TodayPurchase;
import com.wms.today.TodayPurchaseRepository;
import com.wms.today.TodayStock;
import com.wms.today.TodayStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin("*")
public class StockController {
    @Autowired
    StockRepository stockRepository;
    @Autowired
    TodayStockRepository todayStockRepository;
    @Autowired
    TodayPurchaseRepository todayPurchaseRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @PostMapping("inStock")
    public String insert(@RequestBody Stock stock){
        String message = "UnSuccessful";
        List<Stock>list=stockRepository.getQuantity(stock.getBarcode());
        if(list.size()>0){
            Query query=new Query();
            query.addCriteria(Criteria.where("barcode").is(stock.getBarcode()));
            Date date= new Date();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            TodayStock todayStock=new TodayStock(sdf.format(date),stock.getBarcode(),stock.getProduct_name(),
                    stock.getQuantity(),stock.getSize());

            todayStockRepository.save(todayStock);
            Update update=new Update();
            update.set("quantity",stock.getQuantity()+list.get(0).getQuantity());
            mongoTemplate.upsert(query,update,Stock.class);
            message = "update";
        }else {
            Date date= new Date();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            TodayStock todayStock=new TodayStock(sdf.format(date),stock.getBarcode(),stock.getProduct_name(),
                    stock.getQuantity(),stock.getSize());

            todayStockRepository.save(todayStock);
            stockRepository.save(stock);
        message= "Successsful";
        }
        return message;

    }

    @PostMapping("outStock")
    public String insertData(@RequestBody Stock stock){
        String message = "UnSuccessful";
        List<Stock>list1=stockRepository.getQuantity(stock.getBarcode());
        if(list1.size()>0){
            Query query=new Query();
            query.addCriteria(Criteria.where("barcode").is(stock.getBarcode()));
            Update update=new Update();
            update.set("quantity",list1.get(0).getQuantity()-stock.getQuantity());
            Date date =new Date();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            TodayPurchase todayPurchase=new TodayPurchase(sdf.format(date),stock.getBarcode(),
                    stock.getProduct_name(),stock.getQuantity(),stock.getSize());
            todayPurchaseRepository.save(todayPurchase);
            mongoTemplate.upsert(query,update,Stock.class);
            message = "update";
        }else{
            message="list not found";
        }
        return message;
    }
    @GetMapping("getProduct")
    public Map<String,Set<String>>getProduct(@RequestParam("barcode")String barcode){
        List<Stock>list=stockRepository.getQuantity(barcode);
        HashSet<String>uniqueProduct=new HashSet<>();
        for(Stock stock:list){
            uniqueProduct.add(stock.getProduct_name());
        }
        HashMap<String,Set<String>>hMap=new HashMap<>();
        hMap.put("product",uniqueProduct);
        return hMap;

    }

    @GetMapping("getBarcodeData")
    public Map<String,List<Stock>>getBarcodeProduct(@RequestParam("barcode")String barcode){
        List<Stock>list=stockRepository.getQuantity(barcode);
        HashMap<String,List<Stock>>hMap=new HashMap<>();
        hMap.put("product",list);
        return hMap;

    }
    @GetMapping("getAllList")
    public Map<String,List<Stock>>getAllData(){
        List<Stock>list=stockRepository.findAll();
        HashMap<String,List<Stock>>hMap=new HashMap<>();
        hMap.put("data",list);
        return hMap;

    }

    @GetMapping("getCountData")
    public Map<String,List<Map<String,Integer>>> getCountData(){
        List<Stock>list=stockRepository.findAll();
        List<Map<String,Integer>> mainList=new ArrayList<>();
        Map<String,Integer> addCount=new HashMap<>();
        addCount.put("totalStock",list.size());

        List<TodayPurchase>list1= todayPurchaseRepository.findAll();
        addCount.put("totalPurchase",list1.size());

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TodayPurchase> list2 = todayPurchaseRepository.getTodayData(sdf.format(date));
        addCount.put("todayPurchase",list2.size());


        List<TodayStock>list3=todayStockRepository.getTodayData(sdf.format(date));
        addCount.put("todayStock",list3.size());
        mainList.add(addCount);
        Map<String,List<Map<String,Integer>>> hMap=new HashMap<>();
        hMap.put("data",mainList);

        return hMap;
    }
}
