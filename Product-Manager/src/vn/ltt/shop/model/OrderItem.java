package vn.ltt.shop.model;

public class OrderItem {
    private long id;
    private double price;
    private int quantity;
    private long productId;
    private long orderId;

    public OrderItem() {
    }

    public OrderItem(long id, double price, int quantity, long productId, long orderId) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
    }

    public static OrderItem parseOrderItem(String record) {
        OrderItem orderItem = new OrderItem();
        String[] fields = record.split(",");
        orderItem.id = Long.parseLong(fields[0]);
        orderItem.price = Double.parseDouble(fields[1]);
        orderItem.quantity = Integer.parseInt(fields[2]);
        orderItem.productId = Long.parseLong(fields[3]);
        orderItem.orderId = Long.parseLong(fields[4]);
        return orderItem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return this.price * this.quantity;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",
                id,
                price,
                quantity,
                productId,
                orderId
        );
    }
}
