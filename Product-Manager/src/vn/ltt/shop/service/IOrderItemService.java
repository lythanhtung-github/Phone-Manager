package vn.ltt.shop.service;

import vn.ltt.shop.model.OrderItem;
import vn.ltt.shop.utils.TypeSort;

import java.util.List;

public interface IOrderItemService {

    List<OrderItem> findAll();

    List<OrderItem> findAllDelete();

    void add(OrderItem orderItem);

    void update(OrderItem newOrderItem);

    void deleteById(long id);

    void deleteByIdInFileDeleted(long id);

    OrderItem findById(long id);

    OrderItem findByIdDeleted(long id);

    List<OrderItem> findByOrderId(long orderId);

    List<OrderItem> findByOrderIdDeleted(long orderId);

    List<OrderItem> sortById(TypeSort type);

    boolean existById(long id);

    boolean existByIdDeleted(long id);
}