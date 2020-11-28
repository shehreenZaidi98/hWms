package com.wms.today;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class TodayStockController {
@Autowired
    TodayStockRepository todayStockRepository;
@GetMapping("getTodayStock")
    public Map<String, List<TodayStock>>getTodayData(){
    Date date=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    List<TodayStock>list=todayStockRepository.getTodayData(sdf.format(date));
    HashMap<String,List<TodayStock>>hMap=new HashMap<>();
    hMap.put("data",list);
    return hMap;

}

}
