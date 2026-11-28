package app.services;

import app.dto.OrderDto;
import app.entities.Status;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import java.util.List;

public class OrderService {
    private ConnectionPool connectionPool;
    private OrderMapper orderMapper;

    public OrderService(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.orderMapper = new OrderMapper(connectionPool);
    }

    // Returns all orders, or only orders with the given status.
    public List<OrderDto> getOrders(Status status) throws DatabaseException {
        if (status == null){
            return orderMapper.getAllOrders();
        }
        return orderMapper.getOrdersByStatus(status);
    }

    public int countNewOrders() throws DatabaseException {
        return orderMapper.getOrdersByStatus(Status.FORESPOERGSEL).size();
    }



}
