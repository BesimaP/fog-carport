package app.entities;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int userId;
    private int width;
    private int length;
    private int height;
    private Integer shedWidth;   // null when no shed
    private Integer shedLength;  // null when no shed
    private String comment;
    private Status status;
    private double costPrice;
    private double salesPrice;
    private LocalDateTime createdAt;

    // Used when reading an order from the database
    public Order(int orderId, int userId, int width, int length, int height,
                 Integer shedWidth, Integer shedLength, String comment, Status status,
                 double costPrice, double salesPrice, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.width = width;
        this.length = length;
        this.height = height;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.comment = comment;
        this.status = status;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
        this.createdAt = createdAt;
    }

    // Used when the customer sends a new request (the database creates id and createdAt)
    public Order(int userId, int width, int length, int height,
                 Integer shedWidth, Integer shedLength, String comment) {
        this.userId = userId;
        this.width = width;
        this.length = length;
        this.height = height;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.comment = comment;
        this.status = Status.INQUIRY;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getWidth() {
        return width;
    }
    public int getLength() {
        return length;
    }
    public int getHeight() {
        return height;
    }
    public Integer getShedWidth() {
        return shedWidth;
    }
    public Integer getShedLength() {
        return shedLength;
    }
    public String getComment() {
        return comment;
    }
    public Status getStatus() {
        return status;
    }
    public double getCostPrice() {
        return costPrice;
    }
    public double getSalesPrice() {
        return salesPrice;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
