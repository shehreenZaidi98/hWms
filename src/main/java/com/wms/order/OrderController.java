package com.wms.order;

import com.wms.today.TodayPurchase;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        @GetMapping("uploadOrderExcel")

        public void createOrderSheet(HttpServletResponse response1) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
        CellStyle style0 = workbook.createCellStyle();

        style0.setVerticalAlignment(VerticalAlignment.CENTER);
        style0.setAlignment(HorizontalAlignment.CENTER);
        ;
        style0.setBorderBottom(BorderStyle.THIN);
        style0.setBorderTop(BorderStyle.THIN);
        style0.setBorderLeft(BorderStyle.THIN);
        style0.setBorderRight(BorderStyle.THIN);


        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);

        org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 10);

        style0.setFont((org.apache.poi.ss.usermodel.Font) font);
        style0.setWrapText(true);

        style1.setFont((org.apache.poi.ss.usermodel.Font) font1);
        style1.setWrapText(true);



        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Order> orderList = orderRepository.getDate(sdf.format(date));

        try {
            Sheet sheet = workbook.createSheet("TodayPurchase");
            Row row0 = sheet.createRow(0);

            row0.setHeight((short) 600);
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(1, 5000);


            Cell cell0 = row0.createCell(0);
            Cell cell1 = row0.createCell(1);
            Cell cell2 = row0.createCell(2);
            Cell cell3 = row0.createCell(3);


            cell0.setCellStyle(style0);
            cell1.setCellStyle(style0);
            cell2.setCellStyle(style0);
            cell3.setCellStyle(style0);


            cell0.setCellValue("barcode");
            cell1.setCellValue("product_name");
            cell2.setCellValue("quantity");
            cell3.setCellValue("size");


            int j = 1;
            for (Order order :  orderList ) {

                Row row1 = sheet.createRow(j++);
                Cell cell11 = row1.createCell(0);
                Cell cell12 = row1.createCell(1);
                Cell cell13 = row1.createCell(2);

                Cell cell14 = row1.createCell(3);


                cell11.setCellStyle(style1);
                cell12.setCellStyle(style1);
                cell13.setCellStyle(style1);
                cell14.setCellStyle(style1);


                cell11.setCellValue(order.getBarcode());
                cell12.setCellValue(order.getProduct_name());
                cell13.setCellValue(order.getQuantity());
                cell14.setCellValue(order.getSize());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Order> getOrder =orderRepository.findAll();
        try {
            Sheet sheet = workbook.createSheet("Total Order");
            Row row0 = sheet.createRow(0);

            row0.setHeight((short) 600);
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(1, 5000);


            Cell cell0 = row0.createCell(0);
            Cell cell1 = row0.createCell(1);
            Cell cell2 = row0.createCell(2);
            Cell cell3 = row0.createCell(3);


            cell0.setCellStyle(style0);
            cell1.setCellStyle(style0);
            cell2.setCellStyle(style0);
            cell3.setCellStyle(style0);


            cell0.setCellValue("barcode");
            cell1.setCellValue("product_name");
            cell2.setCellValue("quantity");
            cell3.setCellValue("size");


            int j = 1;
            for (Order order : getOrder) {

                Row row1 = sheet.createRow(j++);
                Cell cell11 = row1.createCell(0);
                Cell cell12 = row1.createCell(1);
                Cell cell13 = row1.createCell(2);

                Cell cell14 = row1.createCell(3);


                cell11.setCellStyle(style1);
                cell12.setCellStyle(style1);
                cell13.setCellStyle(style1);
                cell14.setCellStyle(style1);


                cell11.setCellValue(order.getBarcode());
                cell12.setCellValue(order.getProduct_name());
                cell13.setCellValue(order.getQuantity());
                cell14.setCellValue(order.getSize());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        Date date1 = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        response1.setHeader("content-disposition", "attachment;filename=TodayOrder_data" + sdf2.format(date1) + ".xls");
        workbook.write(response1.getOutputStream());


    }


}
