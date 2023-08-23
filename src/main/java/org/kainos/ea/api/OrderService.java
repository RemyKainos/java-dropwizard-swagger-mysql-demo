package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private OrderValidator orderValidator = new OrderValidator();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {
        try{
            List<Order> orderList = orderDao.getAllOrders();

            orderList.stream().filter(order -> order.getCustomerId() == 1).forEach(System.out::println);

            return orderList;
        } catch (Exception e){
            throw new FailedToGetOrdersException();
        }
    }

    public Order getOrderByID(int id) throws FailedToGetOrdersException, OrderDoesNotExistException {
        try{
            Order order = orderDao.getOrderById(id);

            if(order == null) throw new OrderDoesNotExistException();

            return order;
        }catch (Exception e){
            throw new FailedToGetOrdersException();
        }
    }

    public int createOrder(OrderRequest order) throws FailedToCreateOrderException, InvalidOrderException{
        try{
            String valid = orderValidator.isValidOrder(order);

            if(valid != null){
                throw new InvalidOrderException(valid);
            }


            int id = orderDao.createOrder(order);

            if(id == -1){
                throw new FailedToCreateOrderException();
            }

            return id;
        } catch(SQLException e){
            System.err.println(e.getMessage());

            throw new FailedToCreateOrderException();
        }
    }

    public void updateOrder(int id, OrderRequest order) throws InvalidOrderException, FailedToUpdateOrdersException, OrderDoesNotExistException {
        try {
            String valid = orderValidator.isValidOrder(order);

            if(valid != null){
                throw new InvalidOrderException(valid);
            }

            Order orderToUpdate = orderDao.getOrderById(id);

            if(orderToUpdate == null){
                throw new OrderDoesNotExistException();
            }

            orderDao.updateOrder(id, order);
        }catch (SQLException e){
            throw new FailedToUpdateOrdersException();
        }
    }

    public void deleteOrder(int id) throws OrderDoesNotExistException, FailedToDeleteOrderException {
        try{
            Order orderToDelete = orderDao.getOrderById(id);

            if(orderToDelete == null){
                throw new OrderDoesNotExistException();
            }

            orderDao.deleteOrder(id);
        } catch (SQLException e){
            throw new FailedToDeleteOrderException();
        }
    }
}
