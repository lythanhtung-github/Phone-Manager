package vn.ltt.shop.service;

import vn.ltt.shop.model.OrderItem;
import vn.ltt.shop.utils.TypeSort;

import java.util.List;

public interface IOrderItemService {

    List<OrderItem> findAll();

    void add(OrderItem orderItem);

    void update(OrderItem newOrderItem);

    void deleteById(long id);

    OrderItem findById(long id);

    List<OrderItem> findByOrderId(long orderId);

    List<OrderItem> sortById(TypeSort type);

    boolean existById(long id);
}
