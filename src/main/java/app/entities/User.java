package app.entities;

public class User {
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String passwordHash;
    private boolean isAdmin;

    // Used when reading a user from the database
    public User(int userId, String name, String email, String phoneNumber,
                String address, String passwordHash, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    // Used when creating a new customer (the database creates the id, a customer is never admin)
    public User(String name, String email, String phoneNumber, String address, String passwordHash) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.isAdmin = false;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
}
