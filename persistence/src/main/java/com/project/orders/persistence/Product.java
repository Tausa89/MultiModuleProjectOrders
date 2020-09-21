package com.project.orders.persistence;

import com.google.gson.annotations.Expose;
import com.project.orders.persistence.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String productName;
    private BigDecimal price;
    private Category category;

    // TODO Lombok ?!

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (getProductName() != null ? !getProductName().equals(product.getProductName()) : product.getProductName() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(product.getPrice()) : product.getPrice() != null) return false;
        return getCategory() == product.getCategory();
    }

    @Override
    public int hashCode() {
        int result = getProductName() != null ? getProductName().hashCode() : 0;
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
