package main.by.library.dao;

import main.by.library.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> findAllOrder();

    boolean addOrder(Order order);

    boolean deleteOrder(Order order);
}
