package app.services;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;

public class OrderService {

    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    // The mappers are given from outside (dependency injection)
    public OrderService(UserMapper userMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    // Saves the customer first (so the user gets an id), then the order
    public Order placeOrder(Order order) throws DatabaseException {
        userMapper.createUser(order.getUser());
        return orderMapper.createOrder(order);
    }
}
