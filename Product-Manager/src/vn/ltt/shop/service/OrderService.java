package vn.ltt.shop.service;

import vn.ltt.shop.model.Order;
import vn.ltt.shop.utils.CSVUtils;
import vn.ltt.shop.utils.TypeSort;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    public final static String DATA_ORDER_PATH = "data/orders.csv";
    private static OrderService instance;

    private OrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) instance = new OrderService();
        return instance;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        List<String> lines = CSVUtils.read(DATA_ORDER_PATH);
        for (String line : lines) {
            orders.add(Order.parseOrder(line));
        }
        return orders;
    }

    @Override
    public void add(Order newOrder) {
        List<Order> orders = findAll();
        newOrder.setCreatedAt(Instant.now());
        orders.add(newOrder);
        CSVUtils.write(DATA_ORDER_PATH, orders);
    }

    @Override
    public void update(Order newOrder) {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getId() == newOrder.getId()) {
                order.setFullName(newOrder.getFullName());
                order.setPhone(newOrder.getPhone());
                order.setAddress(newOrder.getAddress());
                order.setGrandTotal(newOrder.getGrandTotal());
                order.setUserId(newOrder.getUserId());
                order.setUpdatedAt(Instant.now());
                CSVUtils.write(DATA_ORDER_PATH, orders);
                break;
            }
        }
    }

    @Override
    public void delete(long id) {
        List<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if ((orders.get(i)).getId() == id) {
                orders.remove(orders.get(i));
            }
        }
        CSVUtils.write(DATA_ORDER_PATH, orders);
    }

    @Override
    public Order findById(long id) {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getId() == id)
                return order;
        }
        return null;
    }

    @Override
    public List<Order> findByUserId(long userId) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                ordersFind.add(order);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByFullName(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getFullName().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByFullName(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getFullName().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByPhone(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getPhone().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByPhone(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getPhone().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByAddress(String value) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getAddress().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> findByAddress(String value, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        List<Order> ordersFind = new ArrayList<>();
        for (Order item : orders) {
            if ((item.getAddress().toUpperCase()).contains(value.toUpperCase())) {
                ordersFind.add(item);
            }
        }
        if (ordersFind.isEmpty()) {
            return null;
        }
        return ordersFind;
    }

    @Override
    public List<Order> sortById(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortById(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByUserId(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getUserId() - o2.getUserId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getUserId() - o2.getUserId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByCreatedAt(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByCreatedAt(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByAddress(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByAddress(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByPhone(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByPhone(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByFullName(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByFullName(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }


    @Override
    public List<Order> sortByGrandTotal(TypeSort type) {
        List<Order> orders = findAll();
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getGrandTotal() - o2.getGrandTotal();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getGrandTotal() - o2.getGrandTotal();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public List<Order> sortByGrandTotal(TypeSort type, long userId) {
        List<Order> orders = findOrderByUserId(userId);
        if (type == TypeSort.ASC) {
            orders.sort((o1, o2) -> {
                double result = o1.getGrandTotal() - o2.getGrandTotal();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orders.sort((o1, o2) -> {
                double result = o1.getGrandTotal() - o2.getGrandTotal();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orders;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public List<Order> findOrderByUserId(long userId) {
        List<Order> orders = findAll();
        List<Order> ordersFind = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                ordersFind.add(order);
            }
        }
        return ordersFind;
    }
}
