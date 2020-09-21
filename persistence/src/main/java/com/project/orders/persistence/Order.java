package com.project.orders.persistence;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Customer customer;
    private Product product;
    private int quantity;
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate orderDate;
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate realizationDate;

    // TODO Lombok ?!


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getQuantity() != order.getQuantity()) return false;
        if (getCustomer() != null ? !getCustomer().equals(order.getCustomer()) : order.getCustomer() != null)
            return false;
        if (getProduct() != null ? !getProduct().equals(order.getProduct()) : order.getProduct() != null) return false;
        if (getOrderDate() != null ? !getOrderDate().equals(order.getOrderDate()) : order.getOrderDate() != null)
            return false;
        return getRealizationDate() != null ? getRealizationDate().equals(order.getRealizationDate()) : order.getRealizationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getCustomer() != null ? getCustomer().hashCode() : 0;
        result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
        result = 31 * result + getQuantity();
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        result = 31 * result + (getRealizationDate() != null ? getRealizationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", oderDate=" + orderDate +
                '}';
    }

    public boolean findDateInRange (LocalDate dateFrom, LocalDate dateTo){

        return orderDate.isAfter(dateFrom) && orderDate.isBefore(dateTo);
    }

    public BigDecimal countPrice(){

        BigDecimal discountThreePercent = new BigDecimal("0.97");
        BigDecimal discountTwoPercent = new BigDecimal("0.98");

        if(customer.getAge() < 25){
            return product.getPrice().multiply(discountThreePercent).multiply(new BigDecimal(quantity));
        }
        if(customer.getAge() >= 25 && realizationDate.isBefore(orderDate.plusDays(2))){
            return product.getPrice().multiply(discountTwoPercent).multiply(new BigDecimal(quantity));
        }

        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
