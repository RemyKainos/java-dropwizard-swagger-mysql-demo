package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.core.OrderValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<Order> getAllOrders() throws SQLException{
            Connection c = databaseConnector.getConnection();

            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate FROM `Order`;");

            List<Order> orderList = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("OrderDate")
                );

                orderList.add(order);
            }

            return orderList;
    }

    public Order getOrderById(int id) throws SQLException{
        Connection c = databaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("SELECT OrderID, CustomerId, OrderDate FROM `Order` WHERE OrderId = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        if(rs.next()){
            return new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getDate("OrderDate")
            );
        }

        return null;
    }

    public int createOrder(OrderRequest order) throws SQLException{
        Connection c = databaseConnector.getConnection();

        PreparedStatement st = c.prepareStatement("INSERT INTO `Order`(CustomerId, OrderDate) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, order.getCustomerId());

        Date sqlDate = new Date(order.getOrderDate().getTime());
        st.setDate(2, sqlDate);

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if(rs.next()){
            return rs.getInt(1);
        }

        return -1;
    }

    public void updateOrder(int id, OrderRequest order) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String updateStatement = "UPDATE `Order` SET customerId = ?, orderDate = ?";
        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setInt(1, order.getCustomerId());
        st.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));

        st.executeUpdate();
    }

    public void deleteOrder(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String deleteStatement = "DELETE FROM `Order` WHERE OrderID = ?";
        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1, id);

        st.executeUpdate();
    }
}
