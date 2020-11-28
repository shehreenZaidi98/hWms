package com.wms.today;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

@RestController
public class TodayPurchaseController {
@Autowired
    TodayPurchaseRepository todayPurchaseRepository;
/*@PostMapping("insertTodayPurchase")
    public String insertPurchase(@RequestBody TodayPurchase todayPurchase){
    String message="Unsuccessful";
    todayPurchaseRepository.save(todayPurchase);
    message="Successfull"
    return message;

}*/

    @GetMapping("getTodayData")
    public Map<String, List<TodayPurchase>>getTodayData() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TodayPurchase> list = todayPurchaseRepository.getTodayData(sdf.format(date));
        HashMap<String, List<TodayPurchase>> hMap = new HashMap<>();
        hMap.put("TodayData", list);
        return hMap;
    }

        @GetMapping("getAllPurData")
        public Map<String, List<TodayPurchase>>getAllPurData(){
            List<TodayPurchase>list= todayPurchaseRepository.findAll();
            HashMap<String,List<TodayPurchase>>hMap=new HashMap<>();
            hMap.put("TodayData",list);
            return hMap;


        }


}
