package main.by.library.services.impl;

import main.by.library.dao.OrderDao;
import main.by.library.dao.impl.OrderDaoImpl;
import main.by.library.entity.Order;
import main.by.library.services.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(){
        orderDao = OrderDaoImpl.getInstance();
    }

    @Override
    public List<Order> findAllOrder(int offset) {
        return orderDao.findAllOrder(offset);
    }

    @Override
    public boolean addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        return orderDao.deleteOrder(order);
    }

    @Override
    public int getCountOrder() {
        return orderDao.getCountOrder();
    }

    @Override
    public List<Order> findOrderByCustomerUsername(String username) {
        return orderDao.findOrderByCustomerUsername(username);
    }
}
