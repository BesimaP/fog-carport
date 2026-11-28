package app.dto;

import app.entities.Status;

import java.sql.Timestamp;

// Holds customer name and status name from a JOIN, which the Order entity doesn't have

public class OrderDto {
    private int orderId;
    private String name;
    private int width;
    private int length;
    private Status status;
    private Timestamp createdAt;

    public OrderDto(int orderId, String name, int width, int length, Status status, Timestamp createdAt) {
        this.orderId = orderId;
        this.name = name;
        this.width = width;
        this.length = length;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public Status getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
