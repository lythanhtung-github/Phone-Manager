package vn.ltt.shop.model;

import java.time.Instant;


public class Order {
    private long id;
    private String fullName;
    private String phone;
    private String address;
    private double grandTotal;
    private long userId;
    private Instant createdAt;
    private Instant updatedAt;

    public Order() {
    }

    public Order(long id, String fullName, String phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public static Order parseOrder(String record) {
        Order order = new Order();
        String[] fields = record.split(",");
        order.id = Long.parseLong(fields[0]);
        order.fullName = fields[1];
        order.phone = fields[2];
        order.address = fields[3];
        order.grandTotal = Double.parseDouble(fields[4]);
        order.userId = Long.parseLong(fields[5]);
        order.createdAt = Instant.parse(fields[6]);
        String temp = fields[7];
        if (temp != null && !temp.equals("null")) order.updatedAt = Instant.parse(temp);
        return order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                id,
                fullName,
                phone,
                address,
                grandTotal,
                userId,
                createdAt,
                updatedAt);
    }
}
