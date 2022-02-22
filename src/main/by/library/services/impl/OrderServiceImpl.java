package main.by.library.services.impl;

import main.by.library.dao.OrderDao;
import main.by.library.dao.impl.OrderDaoImpl;
import main.by.library.entity.Order;
import main.by.library.services.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private static OrderServiceImpl instance;

    private OrderServiceImpl(){
        orderDao = OrderDaoImpl.getInstance();
    }

    /**
     * Returns instance if the object has already been created
     *
     * @return instance
     */
    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findAllOrder(int limit,int offset) {
        return orderDao.findAll(limit, offset);
    }

    @Override
    public boolean addOrder(Order order) {
        return orderDao.addNew(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public int getCountOrder() {
        return orderDao.getCount();
    }

    @Override
    public List<Order> findOrderByUsername(String username, int limit, int offset) {
        return orderDao.findOrderByUsername(username, limit, offset);
    }

    @Override
    public Optional<Order> findOrderById(int orderId) {
        return orderDao.findById(orderId);
    }
}
