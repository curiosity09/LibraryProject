package main.by.library.dao;

import main.by.library.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    List<Order> findOrderByUsername(String username, int limit, int offset);

}
