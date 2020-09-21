package com.project.orders.persistence.generator;

import com.github.javafaker.Faker;
import com.project.orders.persistence.Customer;
import com.project.orders.persistence.Order;
import com.project.orders.persistence.Product;
import com.project.orders.persistence.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Setter
@Getter
public class OrderGenerator {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    private Order newOrder;
    // TODO wyrzucic
    private String fileName;


    public OrderGenerator(String fileName) {
        this.fileName = fileName;
        this.newOrder = new Order(getRandomCustomer(),getRandomProduct(),getRandomInteger(),LocalDate.now(),LocalDate.now().plusDays(2));
    }



    private Customer getRandomCustomer(){
        return Customer
                .builder()
                .clientName(faker.name().firstName().toUpperCase())
                .clientSurname(faker.name().lastName().toUpperCase())
                .age(faker.number().numberBetween(18,119))
                .email(faker.internet().safeEmailAddress())
                .build();
    }

    private Product getRandomProduct(){


        return Product
                .builder()
                .productName(faker.food().fruit())
                .price(new BigDecimal(faker.number().numberBetween(1,20000)))
                .category(getRandomCategory())
                .build();
    }

    private Category getRandomCategory(){

        Category [] categories = Category.values();
        return categories[random.nextInt(Category.values().length)];
    }

    private Integer getRandomInteger(){

        return faker.number().numberBetween(1, 50);
    }

    private Integer getRandomInteger(int min, int max){

        return faker.number().numberBetween(min, max);
    }

    private LocalDate getRandomDate(){

        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        LocalDate date = LocalDate.of(2020,getRandomInteger(1,5),getRandomInteger(1,29));
        String formattedDate = date.format(dateFormatter);
        return LocalDate.parse(formattedDate,dateFormatter);
    }



}
