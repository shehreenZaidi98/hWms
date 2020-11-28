package com.wms.today;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TodayStock {
@Id
    private String id;
    private String date;
    private String barcode;
    private String product_name;
    private int quantity;
    private int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TodayStock(String date, String barcode, String product_name, int quantity, int size) {
        this.date = date;
        this.barcode = barcode;
        this.product_name = product_name;
        this.quantity = quantity;
        this.size = size;
    }

    public TodayStock() {
    }
}
