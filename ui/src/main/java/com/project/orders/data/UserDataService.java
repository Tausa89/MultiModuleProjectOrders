package com.project.orders.data;

import com.project.orders.exception.UserDataException;
import com.project.orders.persistence.validator.LocalDateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class UserDataService {

    private static final Scanner scanner = new Scanner(System.in);

    private UserDataService() {
    }

    public static String getString(String message) {

        System.out.println(message);
        return scanner.nextLine();
    }

    public static int getInt(String message) {

        System.out.println(message);
        String value = scanner.nextLine();

        if (!value.matches("[0-9]*")) {
            throw new UserDataException("Wrong input");
        }

        return Integer.parseInt(value);

    }


    public static LocalDate getDate(String message) {

        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

        String date = getString("Provide date using format dd.mm.year");
        System.out.println(message);

        LocalDateValidator validator = new LocalDateValidator();
        if(!validator.isValid(date)){
            throw new UserDataException("Wrong date format");
        }

        return LocalDate.parse(date, dateFormatter);
    }


    public static void close() {
        scanner.close();
    }


}
