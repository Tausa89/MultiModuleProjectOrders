package com.project.orders.persistence.validator;

import com.project.orders.persistence.Order;
import com.project.orders.persistence.validator.generic.AbstractValidator;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderValidator extends AbstractValidator<Order> {

    @Override
    public Map<String, String> validate(Order order) {

        errors.clear();


        var customerValidator = new CustomerValidator();
        var customerErrors = customerValidator.validate(order.getCustomer());
        errors.putAll(customerErrors);

        var productValidator = new ProductValidator();
        var productErrors = productValidator.validate(order.getProduct());
        errors.putAll(productErrors);

        if (!isQuantityValid(order)) {
            errors.put("Quantity", "is not valid");
        }

        if (!isOrderDateValid(order)) {
            errors.put("Realization date", "is not valid");
        }
        return errors;
    }

    private boolean isQuantityValid(Order order) {
        return order.getQuantity() > 0;
    }

    private boolean isOrderDateValid(Order order) {
        return order.getOrderDate().compareTo(LocalDate.now()) == 0 || order.getOrderDate().isAfter(LocalDate.now());
    }
}
