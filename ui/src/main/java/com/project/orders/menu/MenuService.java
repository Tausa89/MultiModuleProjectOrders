package com.project.orders.menu;

import com.project.orders.data.UserDataService;
import com.project.orders.persistence.Customer;
import com.project.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
public class MenuService {


    private final OrdersService ordersService;

    public void mainMenu() {

        while (true) {
            try {
                var option = choseOption();
                switch (option) {
                    case 1 -> optionOne();
                    case 2 -> optionTwo();
                    case 3 -> optionThree();
                    case 4 -> optionFour();
                    case 5 -> optionFive();
                    case 6 -> optionSix();
                    case 7 -> optionSeven();
                    case 8 -> optionEight();
                    case 9 -> optionNine();
                    case 10 -> optionTen();
                    case 11 -> {
                        System.out.println("Have a nice day!");
                        return;
                    }
                    default -> System.out.println("No such option");
                }
            } catch (Exception e) {
                System.out.println("------------------ EXCEPTION ----------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------------");
            }
        }
    }


    private int choseOption() {
        System.out.println("1. Find average price for given time range");
        System.out.println("2. Find highest price for every category");
        System.out.println("3. Find all orders for every client");
        System.out.println("4. Find dates with lowest and highest amounts of orders");
        System.out.println("5. Find client who paid the most");
        System.out.println("6. Calculate total price for all orders including discounts");
        System.out.println("7. Find clients who orders at least given amount of products");
        System.out.println("8. Find most popular category");
        System.out.println("9. Find amount of orders for every month");
        System.out.println("10. Find most popular category for every month");
        System.out.println("11. Close app");

        return UserDataService.getInt("Chose option: ");
    }


    private void optionOne() {
        LocalDate fromDate = UserDataService.getDate("From when you want to start");
        LocalDate toDate = UserDataService.getDate("To when you check");
        BigDecimal averagePriceForDateRange = ordersService.calculateOrdersAveragePriceInDateRange(fromDate, toDate);
        System.out.println("Average price from " + fromDate + " to " + toDate + " is " + averagePriceForDateRange);
    }

    private void optionTwo(){
        var highestPriceForCategory = ordersService.getHighestPriceForCategory();
        highestPriceForCategory.forEach((k,v) -> System.out.println("For category " + k + "highest price is" + v));
    }

    private void optionThree(){
        var productsForEveryCustomer = ordersService.getProductsForEveryCustomer();
        productsForEveryCustomer.forEach((k,v) -> System.out.println("Customer: " + k + "\n Products: " + v));

    }
    private void optionFour(){
        var dateWithHighestAndLowestAmountOfOrders =
                ordersService.getDateWithHighestAndLowestAmountOfOrders();
        dateWithHighestAndLowestAmountOfOrders.forEach((k,v) -> System.out.println(k + ": " + v));
    }

    private void optionFive(){
        var customerWhoPaidMost = ordersService.findCustomerWhoPaidMost();

        System.out.println("Client who paid the most for his orders is " +
                customerWhoPaidMost.getClientName() + " " + customerWhoPaidMost.getClientName());
    }

    private void optionSix(){
        var totalPrice = ordersService.countPriceForAllOrders();

        System.out.println("Total price for are orders is " + totalPrice);
    }

    private void  optionSeven(){
        var amountOfProducts = UserDataService.getInt("Pleas provide amount of products");
        List<Customer> customersWithGivenAmountOfProducts = ordersService.findCustomersWithGivenAmountOfProducts(amountOfProducts);

       customersWithGivenAmountOfProducts.forEach(System.out::println);

    }

    private void  optionEight(){
        var mostWantedCategory = ordersService.mostWantedCategory();

        System.out.println("Most wanted category is " + mostWantedCategory);
    }

    private void  optionNine(){
        var monthIntegerMap = ordersService.groupByMonthWithCountingProducts();

        monthIntegerMap.forEach((k,v) -> System.out.println("In " + k + " total amounts of products was " + v));
    }

    private void optionTen(){
        var monthCategoryMap = ordersService.groupByMonthWithMostPopularCategory();
        monthCategoryMap.forEach((k,v) -> System.out.println("In " + k + " the most popular category was " + v));
    }

}
