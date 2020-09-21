package com.project.orders.persistence;


import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    private String clientName;
    private String clientSurname;
    private int age;
    private String email;

    // TODO Lombok ?!

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getAge() != customer.getAge()) return false;
        if (getClientName() != null ? !getClientName().equals(customer.getClientName()) : customer.getClientName() != null)
            return false;
        if (getClientSurname() != null ? !getClientSurname().equals(customer.getClientSurname()) : customer.getClientSurname() != null)
            return false;
        return getEmail() != null ? getEmail().equals(customer.getEmail()) : customer.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getClientName() != null ? getClientName().hashCode() : 0;
        result = 31 * result + (getClientSurname() != null ? getClientSurname().hashCode() : 0);
        result = 31 * result + getAge();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "clientName='" + clientName + '\'' +
                ", clientSurname='" + clientSurname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
