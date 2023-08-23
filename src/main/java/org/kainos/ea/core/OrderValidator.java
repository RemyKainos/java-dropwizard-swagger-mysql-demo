package org.kainos.ea.core;

import org.kainos.ea.cli.Customer;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.db.CustomerDAO;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class OrderValidator {
    CustomerDAO customerDAO = new CustomerDAO();
    public String isValidOrder(OrderRequest order) throws SQLException {
        Customer customer = customerDAO.getCustomerById(order.getCustomerId());

        if(customer == null) return "Customer does not exist";

        if(order.getOrderDate().before(Date.from(Instant.now().minus(Duration.ofDays(365))))){
            return "Date is older than one year";
        }

        return null;
    }
}
