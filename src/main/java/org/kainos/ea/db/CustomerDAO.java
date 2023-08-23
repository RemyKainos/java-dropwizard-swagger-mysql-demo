package org.kainos.ea.db;

import org.kainos.ea.cli.Customer;
import org.kainos.ea.cli.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    DatabaseConnector databaseConnector = new DatabaseConnector();
    public Customer getCustomerById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("SELECT CustomerID, Name, Address, Phone, Address FROM `Customer` WHERE CustomerId = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        if(rs.next()){
            return new Customer(
                    rs.getInt("CustomerId"),
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("Phone")
            );
        }

        return null;
    }
}
