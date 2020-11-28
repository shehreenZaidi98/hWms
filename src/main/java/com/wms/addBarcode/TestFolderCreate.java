package com.wms.addBarcode;

import java.io.File;

public class TestFolderCreate {
    public static void main(String[] args) {

        String dir = "c://wmsExcel";

        File file = new File(dir);

        // true if the directory was created, false otherwise

        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }

    }
}
