package com.project.orders.persistence.generator;

import com.project.orders.persistence.converter.JsonConverterOrder;
import com.project.orders.persistence.converter.JsonConverterOrders;

public class JsonGenerator {

    private static OrderGenerator orderGenerator;


    public static void generateJsonFileWithGivenFilename(String filename){

        orderGenerator = new OrderGenerator(filename);

        JsonConverterOrder jsonConverterOrder = new JsonConverterOrder(orderGenerator.getFileName());
        jsonConverterOrder.toJson(orderGenerator.getNewOrder());

    }


}
