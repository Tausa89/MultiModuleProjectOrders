package com.project.orders.persistence.converter;

import com.project.orders.persistence.Order;
import com.project.orders.persistence.converter.generic.JsonConverter;

public class JsonConverterOrder extends JsonConverter<Order> {


    public JsonConverterOrder(String jsonFilename) {
        super(jsonFilename);
    }
}
