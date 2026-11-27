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
    }