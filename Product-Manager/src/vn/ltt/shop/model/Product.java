package vn.ltt.shop.model;

import java.time.Instant;

public class Product {
    private long id;
    private String name;
    private double price;
    private int quantity;
    private String manufacturer;
    private Instant createdAt;
    private Instant updatedAt;

    public Product() {
    }

    public Product(long id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    public Product(long id, String name, double price, int quantity, String manufacturer, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product parse(String line) {
        String[] lines = line.split(",");
        long id = Long.parseLong(lines[0]);
        String name = lines[1];
        double price = Double.parseDouble(lines[2]);
        int quantity = Integer.parseInt(lines[3]);
        String manufacturer = lines[4];
        Instant createdAt = Instant.parse(lines[5]);
        String temp = lines[6];
        Instant updatedAt = null;
        if (temp != null && !temp.equals("null"))
            updatedAt = Instant.parse(temp);
        return new Product(id, name, price, quantity, manufacturer, createdAt, updatedAt);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        return id + "," +
                name + "," +
                price + "," +
                quantity + "," +
                manufacturer + "," +
                createdAt + "," +
                updatedAt
                ;
    }
}
