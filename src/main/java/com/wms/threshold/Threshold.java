package com.wms.threshold;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Threshold {
    @Id

    private String id;
    private String barcode;
    private String threshold_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getThreshold_value() {
        return threshold_value;
    }

    public void setThreshold_value(String threshold_value) {
        this.threshold_value = threshold_value;
    }
}
