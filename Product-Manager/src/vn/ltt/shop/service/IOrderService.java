package vn.ltt.shop.service;

import vn.ltt.shop.model.Order;
import vn.ltt.shop.utils.TypeSort;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();

    void add(Order newOrder);

    void update(Order newOrder);

    void delete(long id);

    Order findById(long id);

    List<Order> findByUserId(long id);

    List<Order> findByFullName(String value);

    List<Order> findByPhone(String value);

    List<Order> findByAddress(String value);

    List<Order> sortById(TypeSort type, long userId);

    List<Order> sortById(TypeSort type);

    List<Order> sortByUserId(TypeSort type);

    List<Order> sortByCreatedAt(TypeSort type);

    List<Order> sortByCreatedAt(TypeSort type, long userId);

    List<Order> sortByAddress(TypeSort type);

    List<Order> sortByAddress(TypeSort type, long userId);

    List<Order> sortByPhone(TypeSort type);

    List<Order> sortByPhone(TypeSort type, long userId);

    List<Order> sortByFullName(TypeSort type);

    List<Order> sortByFullName(TypeSort type, long userId);

    List<Order> sortByGrandTotal(TypeSort type);

    List<Order> sortByGrandTotal(TypeSort type, long userId);

    boolean existById(long id);

    List<Order> findOrderByUserId(long userId);
}