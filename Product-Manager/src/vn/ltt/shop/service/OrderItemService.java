package vn.ltt.shop.service;

import vn.ltt.shop.model.OrderItem;
import vn.ltt.shop.utils.CSVUtils;
import vn.ltt.shop.utils.TypeSort;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService {
    public final static String DATA_ORDER_ITEM_PATH = "data/order_items.csv";
    public final static String DATA_ORDER_ITEM_DELETE_PATH = "data/data_delete/order_items_delete.csv";
    private static OrderItemService instance;

    private OrderItemService() {
    }

    public static OrderItemService getInstance() {
        if (instance == null) instance = new OrderItemService();
        return instance;
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        List<String> records = CSVUtils.read(DATA_ORDER_ITEM_PATH);
        for (String record : records) {
            orderItems.add(OrderItem.parseOrderItem(record));
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> findAllDelete() {
        List<OrderItem> orderItems = new ArrayList<>();
        List<String> records = CSVUtils.read(DATA_ORDER_ITEM_DELETE_PATH);
        for (String record : records) {
            orderItems.add(OrderItem.parseOrderItem(record));
        }
        return orderItems;
    }

    @Override
    public void add(OrderItem newOrderItem) {
        List<OrderItem> orderItems = findAll();
        orderItems.add(newOrderItem);
        CSVUtils.write(DATA_ORDER_ITEM_PATH, orderItems);
    }

    @Override
    public void update(OrderItem newOrderItem) {
        List<OrderItem> orderItems = findAll();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == newOrderItem.getId()) {
                orderItem.setPrice(newOrderItem.getPrice());
                orderItem.setQuantity(newOrderItem.getQuantity());
                orderItem.setProductId(newOrderItem.getProductId());
                orderItem.setOrderId(newOrderItem.getOrderId());
                CSVUtils.write(DATA_ORDER_ITEM_PATH, orderItems);
                break;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        List<OrderItem> orderItems = findAll();
        List<OrderItem> orderItemsDelete = findAllDelete();
        for (int i = 0; i < orderItems.size(); i++) {
            if ((orderItems.get(i)).getId() == id) {
                orderItemsDelete.add(orderItems.get(i));
                orderItems.remove(orderItems.get(i));
            }
        }
        CSVUtils.write(DATA_ORDER_ITEM_DELETE_PATH, orderItemsDelete);
        CSVUtils.write(DATA_ORDER_ITEM_PATH, orderItems);
    }

    @Override
    public void deleteByIdInFileDeleted(long id) {
        List<OrderItem> orderItemsDelete = findAllDelete();
        for (int i = 0; i < orderItemsDelete.size(); i++) {
            if ((orderItemsDelete.get(i)).getId() == id) {
                orderItemsDelete.remove(orderItemsDelete.get(i));
            }
        }
        CSVUtils.write(DATA_ORDER_ITEM_DELETE_PATH, orderItemsDelete);
    }

    @Override
    public OrderItem findById(long id) {
        List<OrderItem> orderItems = findAll();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == id) {
                return orderItem;
            }
        }
        return null;
    }

    @Override
    public OrderItem findByIdDeleted(long id) {
        List<OrderItem> orderItems = findAllDelete();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == id) {
                return orderItem;
            }
        }
        return null;
    }

    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        List<OrderItem> orderItems = findAll();
        List<OrderItem> orderItemsFind = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderId() == orderId) {
                orderItemsFind.add(orderItem);
            }
        }
        if (orderItemsFind.isEmpty()) {
            return null;
        }
        return orderItemsFind;
    }

    @Override
    public List<OrderItem> findByOrderIdDeleted(long orderId) {
        List<OrderItem> orderItems = findAllDelete();
        List<OrderItem> orderItemsFind = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderId() == orderId) {
                orderItemsFind.add(orderItem);
            }
        }
        if (orderItemsFind.isEmpty()) {
            return null;
        }
        return orderItemsFind;
    }

    @Override
    public List<OrderItem> sortById(TypeSort type) {
        List<OrderItem> orderItems = findAll();
        if (type == TypeSort.ASC) {
            orderItems.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            orderItems.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return orderItems;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public boolean existByIdDeleted(long id) {
        return findByIdDeleted(id) != null;
    }
}