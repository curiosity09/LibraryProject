package main.by.library.services;

import main.by.library.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrder(int offset);

    boolean addOrder(Order order);

    boolean deleteOrder(Order order);

    int getCountOrder();

    List<Order> findOrderByCustomerUsername(String username);
}
