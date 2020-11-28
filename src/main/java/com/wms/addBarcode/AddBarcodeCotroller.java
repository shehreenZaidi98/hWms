package com.wms.addBarcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AddBarcodeCotroller {
@Autowired
    AddBarcodeRepository addBarcodeRepository;

@PostMapping("insertBarcode")
    public String insertBarcode(@RequestBody AddBarcode addBarcode){
    String message="UnSuccessful";
    AddBarcode addBarcode1=addBarcodeRepository.save(addBarcode);
    if(!addBarcode1.getId().isEmpty()){
        message="Successful";
    }
    return message;
}
    @GetMapping("getBarcode")
    public Map<String, List<AddBarcode>>getBarcodeData(@RequestParam("barcode")String barcode){
       List<AddBarcode>list= addBarcodeRepository.getBarcodeData(barcode);
        HashMap<String,List<AddBarcode>>hMap=new HashMap();
        hMap.put("barcode data",list);
        return hMap;
    }

}
