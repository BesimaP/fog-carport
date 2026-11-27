    package app.persistence;

    import app.entities.User;
    import app.exceptions.DatabaseException;
    import java.sql.*;

    public class UserMapper {

        // Saves a new user in the database and sets the generated id on the user
        public static User createUser(User user,ConnectionPool connectionPool) throws DatabaseException {
            String sql = "INSERT INTO users (name, email, phone_number, address, password_hash) VALUES (?, ?, ?, ?, ?)";
            try(Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
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
