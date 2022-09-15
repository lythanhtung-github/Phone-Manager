package vn.ltt.shop.view;

import vn.ltt.shop.model.Order;
import vn.ltt.shop.model.OrderItem;
import vn.ltt.shop.model.Product;
import vn.ltt.shop.service.*;
import vn.ltt.shop.utils.AppUtils;
import vn.ltt.shop.utils.InstantUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderItemView {
    private final IOrderItemService orderItemService;
    private final IProductService productService;
    private final IOrderService orderService;
    private static final Scanner scanner = new Scanner(System.in);
    ProductView productView = new ProductView();


    public OrderItemView() {
        orderItemService = OrderItemService.getInstance();
        productService = ProductService.getInstance();
        orderService = OrderService.getInstance();
    }

    public void showOrderItem(List<OrderItem> orderItems, InputOption option) {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ GIỎ HÀNG ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s%-9s | %-11s%-19s | %-7s%-11s | %-2s%-10s | %-5s%-17s |\n",
                "", "ID",
                "", "SẢN PHẨM",
                "", "GIÁ",
                "", "SỐ LƯỢNG",
                "", "THÀNH TIỀN"
        );
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (OrderItem orderItem : orderItems) {
            System.out.printf("| %-2s%-12s | %-2s%-28s | %-2s%-16s | %-4s%-8s | %-2s%-20s |\n",
                    "", orderItem.getId(),
                    "", productService.findProductById(orderItem.getProductId()).getName(),
                    "", AppUtils.doubleToVND(orderItem.getPrice()),
                    "", orderItem.getQuantity(),
                    "", AppUtils.doubleToVND(orderItem.getPrice() * orderItem.getQuantity())
            );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressAnyKeyToContinue();
        }
    }

    public void addOrderItem(long orderId) {
        do {
            try {
                productView.showProduct(productService.findAll(), InputOption.UPDATE);
                long id = System.currentTimeMillis() / 1000;
                long productId = inputProductId(InputOption.ADD);
                int quantity = inputQuantity(InputOption.ADD, productId);
                Product product = productService.findProductById(productId);
                double price = product.getPrice();
                OrderItem newOrderItem = new OrderItem(id, price, quantity, productId, orderId);
                List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
                if (orderItems == null) {
                    orderItemService.add(newOrderItem);
                } else {
                    int count = 0;
                    for (OrderItem orderItem : orderItems) {
                        if (orderItem.getProductId() == productId) {
                            int temp = quantity + orderItem.getQuantity();
                            orderItem.setQuantity(temp);
                            orderItemService.update(orderItem);
                            count++;
                        }
                    }
                    if (count == 0) {
                        orderItemService.add(newOrderItem);
                    }
                }
                showOrderItem(orderItemService.findByOrderId(orderId), InputOption.UPDATE);
                setProductQuantity(productId, -orderItemService.findById(id).getQuantity());
                System.out.printf("Đã thêm '%s' số lượng '%s' vào giỏ hàng.\n", product.getName(), quantity);
            } catch (Exception e) {
                System.out.println("Lỗi cú pháp. Vui lòng nhập lại!");
                System.out.println(e.getMessage());
            }
        } while (isRetryAddOrderItem(orderId));
    }

    private boolean isRetryAddOrderItem(long orderId) {
        do {
            System.out.println("===> Chọn 'y' tiếp tục thêm sản phẩm \t|\t 'q' để in hóa đơn \t|\t 't' để thoát.");
            System.out.print(" => ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    return true;
                case "q":
                    setGrandTotal(orderId);
                    printProductInvoice(orderId);
                    return false;
                case "t":
                    List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
                    for (OrderItem orderItem : orderItems) {
                        setProductQuantity(orderItem.getProductId(), orderItem.getQuantity());
                    }
                    System.out.println("Exit the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } while (true);
    }

    public void printProductInvoice(long orderId) {
        List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        Order order = orderService.findById(orderId);

        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ HÓA ĐƠN THANH TOÁN ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░                                                                                                 ░");
        System.out.printf("░                                                                 Thời gian: %16s     ░\n", InstantUtils.instantToString(order.getCreatedAt()));
        System.out.printf("░   Người mua: %-40s                                           ░\n", order.getFullName());
        System.out.printf("░   Số điện thoại: %-40s                                       ░\n", order.getPhone());
        System.out.printf("░   Địa chỉ: %-40s                                             ░\n", order.getAddress());
        System.out.println("░                                                                                                 ░");
        System.out.println("░-------------------------------------------------------------------------------------------------░");
        System.out.printf("░ %-2s%-5s | %-11s%-19s | %-7s%-11s | %-1s%-9s | %-2s%-16s ░\n",
                "", "STT",
                "", "SẢN PHẨM",
                "", "GIÁ",
                "", "SỐ LƯỢNG",
                "", "THÀNH TIỀN"
        );
        System.out.println("░-------------------------------------------------------------------------------------------------░");
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            System.out.printf("░ %-3s%-4s | %-2s%-28s | %-2s%-16s | %-4s%-6s | %-2s%-16s ░\n",
                    "", i + 1,
                    "", productService.findProductById(orderItem.getProductId()).getName(),
                    "", AppUtils.doubleToVND(orderItem.getPrice()),
                    "", orderItem.getQuantity(),
                    "", AppUtils.doubleToVND(orderItem.getPrice() * orderItem.getQuantity())
            );
        }
        System.out.println("░-------------------------------------------------------------------------------------------------░");
        System.out.println("░                                                                                                 ░");
        System.out.printf("░                                                               Tổng tiền: %-20s   ░\n", AppUtils.doubleToVND(order.getGrandTotal()));
        System.out.println("░                                                                                                 ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

    private int inputQuantity(InputOption option, long productId) {
        Product product = productService.findProductById(productId);
        switch (option) {
            case ADD:
                System.out.println("Nhập số lượng sản phẩm: ");
                break;
            case UPDATE:
                System.out.println("Nhập số lượng sản phẩm mới: ");
                break;
        }
        int quantity;
        do {
            quantity = AppUtils.retryParseInt();
            if (quantity < 0) {
                System.out.println("Số lượng sản phẩm không thể âm, vui lòng nhập lại!");
            }
            if (quantity > product.getQuantity()) {
                System.out.printf("Số lượng '%s' vượt quá '%s' sản phẩm hiện có! Vui lòng nhập lại!\n", product.getName(), product.getQuantity());
            }

        } while (quantity < 0 || quantity > product.getQuantity());
        return quantity;
    }

    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case ADD:
                System.out.println("Nhập ID : ");
                break;
            case UPDATE:
                System.out.println("Nhập ID muốn chỉnh sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập ID muốn xóa: ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = orderItemService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("Không tìm thấy! Vui lòng nhập lại");
            }
        } while (isTrue);
        return id;
    }

    private long inputProductId(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập ID sản phẩm: ");
                break;
            case UPDATE:
                System.out.println("Nhập ID sản phẩm muốn chỉnh sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập ID sản phẩm muốn xóa: ");
                break;
        }
        long id;
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = productService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("Không tìm thấy! Vui lòng nhập lại");
            }
        } while (isTrue);
        return id;
    }

    public void updateOrderItem() {
        int option;
        boolean isTrue = true;
        do {
            try {
                long id = inputId(InputOption.UPDATE);
                OrderItem orderItem = orderItemService.findById(id);
                menuUpdateOrderItem();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateProductId(orderItem);
                        isTrue = false;
                        break;
                    case 2:
                        updateQuantity(orderItem);
                        isTrue = false;
                        break;
                    case 3:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai, vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Lỗi cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    private void updateProductId(OrderItem orderItem) {
        do {
            try {
                productView.showProduct(productService.findAll(), InputOption.SHOW);
                long productId = inputProductId(InputOption.UPDATE);
                orderItem.setProductId(productId);
                Product product = productService.findProductById(productId);
                orderItem.setPrice(product.getPrice());
                orderItemService.update(orderItem);
                System.out.println("Chỉnh sửa sản phẩm thành công!");
                setGrandTotal(orderItem.getOrderId());
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                showOrderItem(orderItems, InputOption.UPDATE);
                AppUtils.pressAnyKeyToContinue();
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (AppUtils.isRetry(InputOption.UPDATE));
    }

    private void updateQuantity(OrderItem orderItem) {
        do {
            try {
                int oldQuantity = orderItem.getQuantity();
                int newQuantity = inputQuantity(InputOption.UPDATE, orderItem.getProductId());
                setProductQuantity(orderItem.getProductId(), newQuantity - oldQuantity);
                orderItem.setQuantity(newQuantity);
                orderItemService.update(orderItem);
                setGrandTotal(orderItem.getOrderId());
                System.out.printf("Chỉnh sửa số lượng sản phầm từ %s thành %s.\n", oldQuantity, newQuantity);
                setGrandTotal(orderItem.getOrderId());
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                showOrderItem(orderItems, InputOption.UPDATE);
                AppUtils.pressAnyKeyToContinue();
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (AppUtils.isRetry(InputOption.UPDATE));
    }

    private static void menuUpdateOrderItem() {
        System.out.println("░░░░░░░ CHỈNH SỬA SẢN PHẨM  ░░░░░░░");
        System.out.println("░                                 ░");
        System.out.println("░      1. Đổi sản phẩm.           ░");
        System.out.println("░      2. Chỉnh sửa số lượng.     ░");
        System.out.println("░      3. Trở lại.                ░");
        System.out.println("░      0. Thoát.                  ░");
        System.out.println("░                                 ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Enter your choice: ");
        System.out.print(" => ");
    }

    public void deleteOrderItem() {
        long id = inputId(InputOption.DELETE);
        int option;
        boolean isTrue = true;
        do {
            try {
                menuDeleteOrderItem();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1: {
                        OrderItem orderItem = orderItemService.findById(id);
                        setProductQuantity(orderItem.getProductId(), orderItem.getQuantity());
                        orderItemService.deleteById(id);
                        System.out.println("Xóa thành công!");
                        setGrandTotal(orderItem.getOrderId());
                        isTrue = false;
                        break;
                    }
                    case 2:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai, vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Lỗi cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    private static void menuDeleteOrderItem() {
        System.out.println("░░░░░ BẠN CÓ MUỐN XÓA KHÔNG? ░░░░░");
        System.out.println("░            1. Có.              ░");
        System.out.println("░            2. Không.           ░");
        System.out.println("░            0. Thoát.           ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    public void setGrandTotal(long orderId) {
        Order order = orderService.findById(orderId);
        List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        if (orderItems == null) {
            order.setGrandTotal(0);
        } else {
            double grandTotal = 0;
            for (OrderItem orderItem : orderItems) {
                grandTotal = grandTotal + orderItem.getTotal();
            }
            order.setGrandTotal(grandTotal);
        }
        orderService.update(order);
    }

    public void deleteOrderItemById(long orderItemId) {
        setProductQuantity(orderItemService.findById(orderItemId).getProductId(),
                orderItemService.findById(orderItemId).getQuantity());
        orderItemService.deleteById(orderItemId);
    }

    public void setProductQuantity(long productId, int quantityDifference) {
        Product product = productService.findProductById(productId);
        product.setQuantity(product.getQuantity() + quantityDifference);
        productService.update(product);
    }
}