package main.by.library.dao;

import main.by.library.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> findAllOrder(int offset);

    boolean addOrder(Order order);

    boolean deleteOrder(Order order);

    int getCountOrder();

    List<Order> findOrderByCustomerUsername(String username);

    Order findOrderById(int orderId);
}
