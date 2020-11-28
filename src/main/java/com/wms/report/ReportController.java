package com.wms.report;

import com.wms.order.Order;
import com.wms.order.OrderRepository;
import com.wms.stock.Stock;
import com.wms.stock.StockRepository;
import com.wms.today.TodayPurchase;
import com.wms.today.TodayPurchaseRepository;
import com.wms.today.TodayStock;
import com.wms.today.TodayStockRepository;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ReportController {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    TodayStockRepository todayStockRepository;
    @Autowired
    TodayPurchaseRepository todayPurchaseRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    OrderRepository orderRepository;

   @Scheduled(cron = "* 42 17 * * *")
    public void createProductionSheet() throws IOException, InterruptedException {
        String dir = "c:\\wmsExcel";

        File file = new File(dir);

        // true if the directory was created, false otherwise
        Date date= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

        FileOutputStream fileOutputStream=new FileOutputStream(dir+"\\wmsStock"+sdf.format(date)+".xls");
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }
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

        try {




            List<TodayStock> stockList = todayStockRepository.getTodayData(sdf.format(date));

            try {
                Sheet sheet = workbook.createSheet("TodayStock");
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
                for (TodayStock todayStock : stockList) {

                    Row row1 = sheet.createRow(j++);
                    Cell cell11 = row1.createCell(0);
                    Cell cell12 = row1.createCell(1);
                    Cell cell13 = row1.createCell(2);

                    Cell cell14 = row1.createCell(3);


                    cell11.setCellStyle(style1);
                    cell12.setCellStyle(style1);
                    cell13.setCellStyle(style1);
                    cell14.setCellStyle(style1);


                    cell11.setCellValue(todayStock.getBarcode());
                    cell12.setCellValue(todayStock.getProduct_name());
                    cell13.setCellValue(todayStock.getQuantity());
                    cell14.setCellValue(todayStock.getSize());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Stock> getStock = stockRepository.findAll();
            try {
                Sheet sheet = workbook.createSheet("Total Stock");
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
                for (Stock stock : getStock) {

                    Row row1 = sheet.createRow(j++);
                    Cell cell11 = row1.createCell(0);
                    Cell cell12 = row1.createCell(1);
                    Cell cell13 = row1.createCell(2);

                    Cell cell14 = row1.createCell(3);


                    cell11.setCellStyle(style1);
                    cell12.setCellStyle(style1);
                    cell13.setCellStyle(style1);
                    cell14.setCellStyle(style1);


                    cell11.setCellValue(stock.getBarcode());
                    cell12.setCellValue(stock.getProduct_name());
                    cell13.setCellValue(stock.getQuantity());
                    cell14.setCellValue(stock.getSize());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       // workbook.setHeader("content-disposition", "attachment;filename=TodayStock_data" + sdf.format(date) + ".xls");
        workbook.write(fileOutputStream);

        Thread.sleep(60000);
        SendMail sendMail=new SendMail();
        sendMail.sendMail(dir+"\\wmsStock"+sdf.format(date)+".xls");

}
    @GetMapping("outStockExcel")
    public void createPurchaseSheet(HttpServletResponse response1) throws IOException {
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

            List<TodayPurchase> purchaseList = todayPurchaseRepository.getTodayData(sdf.format(date));

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
                for (TodayPurchase todayPurchase : purchaseList) {

                    Row row1 = sheet.createRow(j++);
                    Cell cell11 = row1.createCell(0);
                    Cell cell12 = row1.createCell(1);
                    Cell cell13 = row1.createCell(2);

                    Cell cell14 = row1.createCell(3);


                    cell11.setCellStyle(style1);
                    cell12.setCellStyle(style1);
                    cell13.setCellStyle(style1);
                    cell14.setCellStyle(style1);


                    cell11.setCellValue(todayPurchase.getBarcode());
                    cell12.setCellValue(todayPurchase.getProduct_name());
                    cell13.setCellValue(todayPurchase.getQuantity());
                    cell14.setCellValue(todayPurchase.getSize());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        List<TodayPurchase> getOrder =todayPurchaseRepository.findAll();
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
            for (TodayPurchase order : getOrder) {

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