package com.wms.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

@RestController
public class OrderController {
@Autowired
    OrderRepository orderRepository;
@Autowired
    MongoTemplate mongoTemplate;

@PostMapping("insertOrder")
    public String insertOrder(@RequestBody Order order){
    String message="Not Inserted" ;
    Date date= new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
   order.setDate(sdf.format(date));
    Order order1=orderRepository.save(order);
    if(!order1.getId().isEmpty()){
        message="insert";
    }
    return message;
}
    @GetMapping("getDate")
    public Map<String, List<Order>>getOrder(@RequestParam("date")String date){
    List<Order>list=orderRepository.getDate(date);
    HashMap<String,List<Order>>hMap=new HashMap<>();
    hMap.put("data",list);
    return hMap;
    }

}
