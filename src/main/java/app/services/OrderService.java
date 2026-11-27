    package app.services;

    import app.entities.Order;
    import app.exceptions.DatabaseException;
    import app.persistence.ConnectionPool;
    import app.persistence.OrderMapper;
    import app.persistence.UserMapper;

    import java.sql.Connection;
    import java.sql.SQLException;

    public class OrderService {

        private final ConnectionPool connectionPool;
        private final UserMapper userMapper;
        private final OrderMapper orderMapper;

        // The connection pool and mappers are given from outside (dependency injection)
        public OrderService(ConnectionPool connectionPool, UserMapper userMapper, OrderMapper orderMapper) {
            this.connectionPool = connectionPool;
            this.userMapper = userMapper;
            this.orderMapper = orderMapper;
        }

        // Saves the user and the order in one transaction (all or nothing)
        public Order placeOrder(Order order) throws DatabaseException {
            try (Connection connection = connectionPool.getConnection()){
                try {
                    connection.setAutoCommit(false);
                    userMapper.createUser(order.getUser(), connection);
                    orderMapper.createOrder(order,connection);
                    connection.commit();
                    return order;
                } catch (DatabaseException e){
                    connection.rollback();
                    throw e;
                }
            } catch (SQLException e){
                throw new DatabaseException("Kunne ikke gemme bestillingen", e.getMessage());
            }
        }
    }
