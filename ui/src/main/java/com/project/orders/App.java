package com.project.orders;

import com.project.orders.menu.MenuService;
import com.project.orders.service.OrdersService;

public class App {

    public static void main(String[] args) {


        final String FILE_NAME_ONE = "jsonDataFile.json";
//        final String FILE_NAME_TWO = "jsonDataFile2.json";
//        final String FILE_NAME_THREE = "jsonDataFile3.json";

        var orderService = new OrdersService(FILE_NAME_ONE);
        var menuService = new MenuService(orderService);
        menuService.mainMenu();

//        DataGenerator.generateJsonFileWithGivenFilename("jsonDataFile3");



    }

}
