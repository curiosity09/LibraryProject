package main.by.library.services;

import main.by.library.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllOrder(int limit, int offset);

    boolean addOrder(Order order);

    boolean deleteOrder(Order order);

    int getCountOrder();

    List<Order> findOrderByUsername(String username, int limit, int offset);

    Optional<Order> findOrderById(int orderId);
}
