package com.project.orders.persistence.validator;

import com.project.orders.persistence.Customer;
import com.project.orders.persistence.validator.generic.AbstractValidator;

import java.util.Map;

public class CustomerValidator extends AbstractValidator<Customer> {

    @Override
    public Map<String, String> validate(Customer customer) {
        errors.clear();

        if (!isClientNameValid(customer)) {
            errors.put("Customer Name", "is not valid");
        }
        if (!isClientSureNameValid(customer)){
            errors.put("Customer Surname", "is not valid");
        }

        if (!isClientAgeValid(customer)){
            errors.put("Customer age", "is not valid");
        }

        if(!isEmailValid(customer)){
            errors.put("Customer email", "is not valid");
        }

        return errors;
    }

    @Override
    public boolean hasErrors() {
        return super.hasErrors();
    }


    private boolean isClientNameValid(Customer customer) {

        return customer.getClientName().matches("[A-Z\\s]+");
    }

    private boolean isClientSureNameValid(Customer customer){

        return customer.getClientSurname().matches("[A-Z\\s]+");
    }

    private boolean isClientAgeValid(Customer customer) {


        return customer.getAge() >= 18 && customer.getAge()<120;

    }

    private boolean isEmailValid(Customer customer) {

        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return customer.getEmail().matches(regex);
    }
}
