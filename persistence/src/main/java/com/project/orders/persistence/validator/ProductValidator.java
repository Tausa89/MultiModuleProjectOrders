package com.project.orders.persistence.validator;

import com.project.orders.persistence.Product;
import com.project.orders.persistence.validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class ProductValidator extends AbstractValidator<Product> {

    @Override
    public Map<String, String> validate(Product product) {
        errors.clear();


        if (!isProductNameValid(product)){
            errors.put("Product name", "is not valid");
        }

        if(!isPriceValid(product)){
            errors.put("Price", "is not valid");
        }

        return errors;
    }


    private boolean isProductNameValid(Product product){

        return product.getProductName().matches("[A-Z\\s]+");
    }

    private boolean isPriceValid(Product product){

        return product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }




}
