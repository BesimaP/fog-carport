package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import java.sql.*;

public class UserMapper {

    private final ConnectionPool connectionPool;

    // The connection pool is given from outside (dependency injection)
    public UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // Saves a new order using the given connection (so it can be part of a transaction)
    public User createUser(User user, Connection connection) throws DatabaseException {
        String sql = "INSERT INTO users (name, email, phone_number, address, password_hash) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPasswordHash());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            user.setUserId(id);

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette bruger", e.getMessage());
        }
        return user;
    }

    // Finds a user by email (returns null if no user has that email)
    public User getUserByEmail(String email, Connection connection) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new User(rs.getInt("user_id"), rs.getString("name"),rs.getString("email"),rs.getString("phone_number"),rs.getString("address"),rs.getString("password_hash"), rs.getBoolean("is_admin"));
            }

        } catch (SQLException e){
            throw new DatabaseException("Kunne ikke finde bruger", e.getMessage());
        }
        return user;
    }
}