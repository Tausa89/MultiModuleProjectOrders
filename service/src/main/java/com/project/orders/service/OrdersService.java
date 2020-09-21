package com.project.orders.service;

import com.project.orders.persistence.Customer;
import com.project.orders.persistence.Order;
import com.project.orders.persistence.Product;
import com.project.orders.persistence.converter.JsonConverterOrders;
import com.project.orders.persistence.enums.Category;
import com.project.orders.persistence.validator.OrderValidator;
import com.project.orders.service.exception.OrdersServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class OrdersService {

    private final List<Order> orders;


    public OrdersService(String ... filenames) {
        this.orders = init(filenames);
    }

    private List<Order> init(String[] filenames) {

        return Arrays
                .stream(filenames)
                .flatMap(filename -> {
                    return new JsonConverterOrders(filename)
                            .fromJson()
                            .orElseThrow(() -> new OrdersServiceException("cannot get cars from file " + filename))
                            .stream()
                            .filter(order -> {
                                var validator = new OrderValidator();
                                var errors = validator.validate(order);
                                if (validator.hasErrors()) {
                                    var errorsMessage = errors
                                            .entrySet()
                                            .stream()
                                            .map(e -> e.getKey() + ": " + e.getValue())
                                            .collect(Collectors.joining("\n"));
                                    System.out.println("----------------------- validation error ------------------------");
                                    System.out.println(errorsMessage);
                                    System.out.println("-----------------------------------------------------------------");
                                }
                                return !validator.hasErrors();
                            });
                }).collect(Collectors.toList());

    }


    public Map<Category, Product> getHighestPriceForCategory() {
        return orders
                .stream()
                .collect(Collectors.groupingBy(
                        order -> order.getProduct().getCategory(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(o -> o.getProduct().getPrice())),
                                orderOp -> orderOp.orElseThrow().getProduct())));
    }

    public BigDecimal calculateOrdersAveragePriceInDateRange(LocalDate dateFrom, LocalDate dateTo) {


        if(dateFrom == null){
            throw new OrdersServiceException("date is null");
        }

        if(dateTo == null){
            throw new OrdersServiceException("date is null");
        }

        var filteredOrders = orders.stream().filter(order -> order.findDateInRange(dateFrom, dateTo)).collect(Collectors.toList());

//        var filteredOrders = orders
//                .stream()
//                // TODO ZASTANOW SIE CZY TUTAJ NIE LAMIESZ TDA
//                .filter(p -> p.getOderDate().compareTo(dateFrom) >= 0 && p.getOderDate().compareTo(dateTo) <= 0)
//                .collect(Collectors.toList());

        var sum = filteredOrders
                .stream()
                .map(order -> order.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var size = filteredOrders.size();

        if (size == 0) {
            throw new IllegalStateException("...");
        }

        return sum.divide(BigDecimal.valueOf(size), 2, RoundingMode.DOWN);


    }

    public Map<Customer, List<Product>> getProductsForEveryCustomer() {

        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.mapping(Order::getProduct,
                                Collectors.toList())));

    }

    public Map<String, LocalDate> getDateWithHighestAndLowestAmountOfOrders() {

        Map<String, LocalDate> result = new HashMap<>();

        var collect = orders
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate));
        Set<LocalDate> dates = collect.keySet();

        LocalDate dateWithMinimumOrdersNumber = dates
                .stream()
                .min(Comparator.comparingInt(o -> collect.get(o).size()))
                .orElseThrow(null);
        LocalDate dateWithMaximumOrdersNumber = dates
                .stream()
                .max(Comparator.comparingInt(o -> collect.get(o).size()))
                .orElseThrow(null);

        result.put("Date with minimum orders number", dateWithMinimumOrdersNumber);
        result.put("Date with maximum orders number", dateWithMaximumOrdersNumber);


        return result;


    }

    public Customer findCustomerWhoPaidMost() {
        return orders
                .stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.collectingAndThen(
                                Collectors.mapping(o -> o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())), Collectors.toList()),
                                prices -> prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        /*return orders.stream().collect(Collectors.groupingBy(Order::getCustomer))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .stream()
                        .map(p -> p.getProduct().getPrice().multiply(new BigDecimal(p.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .entrySet()
                .stream().max(Map.Entry.comparingByValue()).orElseThrow(NullPointerException::new).getKey();*/

    }

    public Category mostWantedCategory() {

        return orders
                .stream()
                .collect(Collectors.groupingBy(
                        p -> p.getProduct().getCategory(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(NullPointerException::new)
                .getKey();

    }

    public Map<Month, Integer> groupByMonthWithCountingProducts() {

        return orders
                .stream()
                .collect(Collectors.groupingBy(p -> p.getOrderDate().getMonth(),
                        Collectors.summingInt(Order::getQuantity)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2.compareTo(e1)));
    }

    public Map<Month, Category> groupByMonthWithMostPopularCategory() {
        return orders
                .stream()
                .collect(Collectors.groupingBy(
                        p -> p.getOrderDate().getMonth(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(order -> order.getProduct().getCategory()),
                                entries -> entries
                                        .entrySet()
                                        .stream()
                                        .max(Comparator.comparingInt(e -> e.getValue().size()))
                                        .orElseThrow()
                                        .getKey()
                        )
                ));
    }

    public List<Customer> findCustomersWithGivenAmountOfProducts(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(".....");
        }

        return orders
                .stream()
                .filter(order -> order.getQuantity() >= amount)
                .map(Order::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }


    public BigDecimal countPriceForAllOrders() {


        return orders
                .stream()
                .map(Order::countPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

//    private BigDecimal countPriceWithUnderTwentyFiveDiscount() {
//        BigDecimal underTwentyFiveDiscount = new BigDecimal("0.97");
//        return orders
//                .stream().filter(c -> c.getCustomer().getAge() < 25)
//                .map(p -> p.getProduct().getPrice().multiply(new BigDecimal(p.getQuantity()).multiply(underTwentyFiveDiscount)))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//    }
//
//    private BigDecimal countPriceWithRealizationDiscount() {
//        BigDecimal fastRealizationDiscount = new BigDecimal("0.98");
//        return orders
//                .stream()
//                .filter(c -> c.getRealizationDate().compareTo(LocalDate.now().plusDays(2)) > 0 && c.getCustomer().getAge() > 25)
//                .map(p -> p.getProduct().getPrice().multiply(new BigDecimal(p.getQuantity()).multiply(fastRealizationDiscount)))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal countPriceWithoutDiscount() {
//
//
//        System.out.println(orders.get(0).getRealizationDate().compareTo(LocalDate.now().plusDays(2)));
//        System.out.println(orders.get(0).getRealizationDate());
//        System.out.println(LocalDate.now().plusDays(2));
//
//        return orders
//                .stream()
//                .filter(c -> c.getCustomer().getAge() > 25 &&
//                        c.getRealizationDate().compareTo(LocalDate.now().plusDays(2)) < 0)
//                .map(p -> p.getProduct().getPrice().multiply(new BigDecimal(p.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }



}
