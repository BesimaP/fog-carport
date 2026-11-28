package app.persistence;

import app.dto.OrderDto;
import app.entities.Status;
import app.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(OrderMapper.class);

    public OrderMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    // Returns orders with the given status, newest first.
    public List<OrderDto> getOrdersByStatus(Status status) throws DatabaseException {
        List<OrderDto> orders = new ArrayList<>();

        String query = "SELECT orders.order_id, users.name, orders.width, orders.length, orders.height, " +
                "status.name AS status_name, orders.created_at " +
                "FROM orders " +
                "JOIN users ON orders.user_id = users.user_id " +
                "JOIN status ON orders.status_id = status.status_id " +
                "WHERE status.name = ? " +
                "ORDER BY orders.created_at DESC";

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                orders.add(toOrderDto(rs)); 
            }

        } catch (SQLException e){
            logger.error("Kunne ikke hente ordrer med status {}", status, e);
            throw new DatabaseException("Kunne ikke hente forespørgsler"); 
        }
        return orders;
    }

    public List<OrderDto> getAllOrders() throws DatabaseException {
        List<OrderDto> orders = new ArrayList<>();

        String query = "SELECT orders.order_id, users.name, orders.width, orders.length, orders.height, " +
                "status.name AS status_name, orders.created_at " +
                "FROM orders " +
                "JOIN users ON orders.user_id = users.user_id " +
                "JOIN status ON orders.status_id = status.status_id " +
                "ORDER BY orders.created_at DESC";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                orders.add(toOrderDto(rs));
            }
        } catch (SQLException e) {
            logger.error("Kunne ikke hente alle ordrer", e);
            throw new DatabaseException("Kunne ikke hente forespørgsler");
        }

        return orders;
    }


    // Converts one database row into an OrderDto.
    private OrderDto toOrderDto(ResultSet rs) throws SQLException {
        int orderId = rs.getInt("order_id");
        String customerName = rs.getString("name");
        int width = rs.getInt("width");
        int length = rs.getInt("length");
        Status status = Status.valueOf(rs.getString("status_name"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        return new OrderDto(orderId, customerName, width, length, status, createdAt);
    }

}
