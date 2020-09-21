package com.project.orders.persistence.validator;

import com.project.orders.persistence.validator.generic.DataValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateValidator implements DataValidator {


    private final String europeanDatePattern = "dd.MM.yyyy";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);


    @Override
    public boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
