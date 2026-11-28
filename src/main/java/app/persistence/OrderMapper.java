package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import java.sql.*;

public class OrderMapper {

    private final ConnectionPool connectionPool;

    // The connection pool is given from outside (dependency injection)
    public OrderMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // Saves a new order in the database and sets the generated id on the order
    public Order createOrder(Order order, Connection connection) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, width, length, height, shed_width, shed_length, comment, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, order.getUser().getUserId());
            ps.setInt(2, order.getWidth());
            ps.setInt(3, order.getLength());
            ps.setInt(4, order.getHeight());
            ps.setObject(5, order.getShedWidth());
            ps.setObject(6, order.getShedLength());
            ps.setString(7, order.getComment());
            ps.setInt(8, order.getStatus().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette ordre", e.getMessage());
        }
        return order;
    }
}