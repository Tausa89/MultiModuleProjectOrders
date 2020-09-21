package com.project.orders.persistence.converter;

import com.project.orders.persistence.Order;
import com.project.orders.persistence.converter.generic.JsonConverter;

import java.util.List;


public class JsonConverterOrders extends JsonConverter<List<Order>> {

    public JsonConverterOrders(String jsonFilename) {
        super(jsonFilename);
    }

}
