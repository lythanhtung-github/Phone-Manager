package vn.ltt.shop.view;

import vn.ltt.shop.model.Order;
import vn.ltt.shop.model.OrderItem;
import vn.ltt.shop.model.Role;
import vn.ltt.shop.model.User;
import vn.ltt.shop.service.*;
import vn.ltt.shop.utils.AppUtils;
import vn.ltt.shop.utils.InstantUtils;
import vn.ltt.shop.utils.TypeSort;
import vn.ltt.shop.utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private final IOrderService orderService;
    private final IOrderItemService orderItemService;
    private final IUserService userService;
    private static final Scanner scanner = new Scanner(System.in);
    private final UserView userView = new UserView();
    private final OrderItemView orderItemView = new OrderItemView();

    public OrderView() {
        orderService = OrderService.getInstance();
        orderItemService = OrderItemService.getInstance();
        userService = UserService.getInstance();
    }

    public void statisticalByDay() {
        System.out.println("░░░░░░░░░░░░░ THỐNG KÊ THEO NGÀY ░░░░░░░░░░░░");
        String day = inputDay();
        List<Order> ordersFind = new ArrayList<>();
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            String createdDate = InstantUtils.instantToStringDay(order.getCreatedAt());
            if (day.equals(createdDate)) {
                ordersFind.add(order);
            }
        }
        System.out.printf("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ DOANH THU NGÀY %s ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n", day);
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░ %-2s%-5s | %-8s%-16s | %-5s%-9s | %-6s%-14s | %-5s%-17s ░\n",
                "", "STT",
                "", "KHÁCH HÀNG",
                "", "SĐT",
                "", "ĐỊA CHỈ",
                "", "THÀNH TIỀN"
        );
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        double revenueTotal = 0;
        for (int i = 0; i < ordersFind.size(); i++) {
            Order order = ordersFind.get(i);
            revenueTotal += order.getGrandTotal();
            System.out.printf("░ %-3s%-4s | %-2s%-22s | %-2s%-12s | %-2s%-18s | %-2s%-20s ░\n",
                    "", i + 1,
                    "", order.getFullName(),
                    "", order.getPhone(),
                    "", order.getAddress(),
                    "", AppUtils.doubleToVND(order.getGrandTotal())
            );
        }
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░                                                         TỔNG DOANH THU: %-20s%6s  ░\n", AppUtils.doubleToVND(revenueTotal), "");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

    public void statisticalByMonth() {
        System.out.println("░░░░░░░░░░░░░ THỐNG KÊ THEO THÁNG ░░░░░░░░░░░░");
        String month = inputMonth();
        List<Order> ordersFind = new ArrayList<>();
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            String createdDate = InstantUtils.instantToStringMonth(order.getCreatedAt());
            if (month.equals(createdDate)) {
                ordersFind.add(order);
            }
        }
        System.out.printf("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ DOANH THU THÁNG %s ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n", month);
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░ %-2s%-5s | %-8s%-16s | %-5s%-9s | %-6s%-14s | %-5s%-17s ░\n",
                "", "STT",
                "", "KHÁCH HÀNG",
                "", "SĐT",
                "", "ĐỊA CHỈ",
                "", "THÀNH TIỀN"
        );
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        double revenueTotal = 0;
        for (int i = 0; i < ordersFind.size(); i++) {
            Order order = ordersFind.get(i);
            revenueTotal += order.getGrandTotal();
            System.out.printf("░ %-3s%-4s | %-2s%-22s | %-2s%-12s | %-2s%-18s | %-2s%-20s ░\n",
                    "", i + 1,
                    "", order.getFullName(),
                    "", order.getPhone(),
                    "", order.getAddress(),
                    "", AppUtils.doubleToVND(order.getGrandTotal())
            );
        }
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░                                                         TỔNG DOANH THU: %-20s%6s  ░\n", AppUtils.doubleToVND(revenueTotal), "");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

    public void statisticalByYear() {
        System.out.println("░░░░░░░░░░░░░ THỐNG KÊ THEO NĂM ░░░░░░░░░░░░");
        String year = inputYear();
        List<Order> ordersFind = new ArrayList<>();
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            String createdDate = InstantUtils.instantToStringYear(order.getCreatedAt());
            if (year.equals(createdDate)) {
                ordersFind.add(order);
            }
        }
        System.out.printf("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ DOANH THU NĂM %s ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n", year);
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░ %-2s%-5s | %-8s%-16s | %-5s%-9s | %-6s%-14s | %-5s%-17s ░\n",
                "", "STT",
                "", "KHÁCH HÀNG",
                "", "SĐT",
                "", "ĐỊA CHỈ",
                "", "THÀNH TIỀN"
        );
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        double revenueTotal = 0;
        for (int i = 0; i < ordersFind.size(); i++) {
            Order order = ordersFind.get(i);
            revenueTotal += order.getGrandTotal();
            System.out.printf("░ %-3s%-4s | %-2s%-22s | %-2s%-12s | %-2s%-18s | %-2s%-20s ░\n",
                    "", i + 1,
                    "", order.getFullName(),
                    "", order.getPhone(),
                    "", order.getAddress(),
                    "", AppUtils.doubleToVND(order.getGrandTotal())
            );
        }
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░                                                                                                     ░");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.printf("░                                                         TỔNG DOANH THU: %-20s%6s  ░\n", AppUtils.doubleToVND(revenueTotal), "");
        System.out.println("░-----------------------------------------------------------------------------------------------------░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }


    private String inputDay() {
        System.out.println("Nhập ngày (VD: 02-09-2022): ");
        System.out.print(" => ");
        String day;
        while (!ValidateUtils.isDayValid(day = scanner.nextLine().trim())) {
            System.out.println("Ngày, tháng, năm được phân tách bởi dấu '-' (VD: VD: 02-09-2022)");
            System.out.print(" => ");
        }
        return day;
    }

    private String inputMonth() {
        System.out.println("Nhập tháng (VD: 09-2022): ");
        System.out.print(" => ");
        String month;
        while (!ValidateUtils.isMonthValid(month = scanner.nextLine().trim())) {
            System.out.println("Tháng, năm được phân tách bởi dấu '-' (VD: 09-2022).");
            System.out.print(" => ");
        }
        return month;
    }

    private String inputYear() {
        System.out.println("Nhập năm (VD: 2022): ");
        System.out.print(" => ");
        String year;
        while (!ValidateUtils.isYearValid(year = scanner.nextLine().trim())) {
            System.out.println("Năm gồm 4 chữ số (VD: 2022).");
            System.out.print(" => ");
        }
        return year;
    }

    public void showOrder(List<Order> orders, InputOption option) {

        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ DANH SÁCH ĐƠN HÀNG ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s%-9s | %-8s%-18s | %-6s%-10s | %-4s%-12s | %-7s%-15s | %-11s%-24s | %-4s%-18s | %-2s%-20s |\n",
                "", "ID",
                "", "KHÁCH HÀNG",
                "", "SĐT",
                "", "ĐỊA CHỈ",
                "", "TỔNG TIỀN",
                "", "NHÂN VIÊN (ID)",
                "", "THỜI GIAN TẠO",
                "", "THỜI GIAN CHỈNH SỬA"
        );
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Order order : orders) {
            System.out.printf("| %-2s%-12s | %-3s%-23s | %-3s%-13s | %-2s%-14s | %-4s%-18s | %-2s%-33s | %-2s%-20s | %-2s%-20s |\n",
                    "", order.getId(),
                    "", order.getFullName(),
                    "", order.getPhone(),
                    "", order.getAddress(),
                    "", AppUtils.doubleToVND(order.getGrandTotal()),
                    "", userView.findNameById(order.getUserId()) + " (" + order.getUserId() + ")",
                    "", InstantUtils.instantToString(order.getCreatedAt()),
                    "", order.getUpdatedAt() == null ? "" : InstantUtils.instantToString(order.getUpdatedAt())
            );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        if (option == InputOption.SHOW) {
            showAllItemOfOrder();
        }
    }

    public void showAllItemOfOrder() {
        boolean isTrue = true;
        long orderId;
        do {
            System.out.println("Chọn 'y' để xem chi tiết đơn hàng \t|\t 'i' để in hóa đơn \t|\t 'q' để trở lại.");
            System.out.print(" => ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    System.out.println("░░░░░ XEM CHI TIẾT ĐƠN HÀNG ░░░░");
                    orderId = inputId(InputOption.SHOW);
                    orderItemView.showOrderItem(orderItemService.findByOrderId(orderId), InputOption.UPDATE);
                    break;
                case "i":
                    System.out.println("░░░░░░░ IN HÓA ĐƠN ░░░░░░");
                    orderId = inputId(InputOption.SHOW);
                    orderItemView.printProductInvoice(orderId);
                    break;
                case "q":
                    isTrue = false;
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } while (isTrue);
    }

    public void addOrder(long userId) {
        long orderId = 0;
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                Order order = new Order(id, fullName, phone, address);
                order.setUserId(userId);
                orderService.add(order);
                System.out.println("Tạo đơn hàng thành công! Thêm sản phẩm vào giỏ hàng.");
                orderItemView.addOrderItem(order.getId());
                orderId = id;
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isRetryOrder(orderId));
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập địa chỉ (Ký tự đầu của từng từ phải viết hoa, VD: Huế)");
                break;
            case UPDATE:
                System.out.println("Nhập địa chỉ mới: ");
                break;
        }
        System.out.print(" => ");
        String address;
        while (!ValidateUtils.isAddressValid(address = scanner.nextLine())) {
            System.out.println("Địa chỉ không đúng định dạng, vui lòng nhập lại!");
            System.out.print(" => ");
        }
        return address;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập tên khách hàng (Ký tự đầu của từng từ phải ghi hoa, VD: Lý Thanh Tùng)");
                break;
            case UPDATE:
                System.out.println("Nhập tên mới: ");
                break;
        }
        System.out.print(" => ");
        String fullName;
        while (!ValidateUtils.isFullNameValid(fullName = scanner.nextLine())) {
            System.out.println("Ký tự đầu của từng từ phải ghi hoa, VD: Lý Thanh Tùng");
            System.out.print(" => ");
        }
        return fullName;
    }

    private String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại (VD: 0123456789)");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại mới: ");
                break;
        }
        System.out.print(" => ");
        String phone;
        while (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
            System.out.println("Số điện thoại gồm 10 số và bắt đầu bằng chữ số 0, VD: 0123456789");
            System.out.print(" => ");
        }
        return phone;
    }

    public void updateOrder(long userId) {
        User user = userService.findById(userId);
        int option;
        boolean isTrue = true;
        do {
            try {
                if (user.getRole() == Role.ADMIN)
                    showOrder(orderService.findAll(), InputOption.UPDATE);
                if (user.getRole() == Role.USER)
                    showOrder(orderService.findOrderByUserId(userId), InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                Order order = orderService.findById(id);
                menuUpdateOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateFullName(order);
                        break;
                    case 2:
                        updatePhone(order);
                        break;
                    case 3:
                        updateAddress(order);
                        break;
                    case 4:
                        updateOrderItem(order);
                        break;
                    case 5:
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
                isTrue = option != 6 && AppUtils.isRetry(InputOption.UPDATE);

            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);

    }

    private void updateOrderItem(Order order) {
        boolean isTrue = true;
        String option;
        do {
            try {
                if (orderItemService.findByOrderId(order.getId()) != null) {
                    orderItemView.showOrderItem(orderItemService.findByOrderId(order.getId()), InputOption.UPDATE);
                    System.out.println("===> Chọn 't' để thêm sản phẩm \t|\t  'y' để sửa sản phẩm \t|\t 'x' để xóa sản phẩm trong giỏ hàng");
                    option = scanner.nextLine();
                    switch (option) {
                        case "t":
                            orderItemView.addOrderItem(order.getId());
                            isTrue = false;
                            break;
                        case "y":
                            orderItemView.updateOrderItem();
                            isTrue = false;
                            break;
                        case "x":
                            orderItemView.deleteOrderItem();
                            isTrue = false;
                            break;
                        default:
                            System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                            break;
                    }
                } else {
                    System.out.println("Giỏ hàng rỗng!");
                    System.out.println("===> Chọn 'y' để thêm sản phẩm \t|\t 'q' để quay lại");
                    option = scanner.nextLine();
                    switch (option) {
                        case "y":
                            orderItemView.addOrderItem(order.getId());
                            break;
                        case "q":
                            isTrue = false;
                            break;
                        default:
                            System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                            break;
                    }
                    isTrue = false;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    private void updateAddress(Order order) {
        String address = inputAddress(InputOption.UPDATE);
        order.setAddress(address);
        orderService.update(order);
        System.out.println("Cập nhật địa chỉ thành công!");
        AppUtils.pressAnyKeyToContinue();
    }

    private void updatePhone(Order order) {
        String phone = inputPhone(InputOption.UPDATE);
        order.setPhone(phone);
        orderService.update(order);
        System.out.println("Cập nhật số điện thoại thành công!");
        AppUtils.pressAnyKeyToContinue();
    }

    private void updateFullName(Order order) {
        String fullName = inputFullName(InputOption.UPDATE);
        order.setFullName(fullName);
        orderService.update(order);
        System.out.println("Cập nhật tên khách hàng thành công!");
        AppUtils.pressAnyKeyToContinue();
    }

    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case SHOW:
                System.out.println("Nhập ID đơn hàng: ");
                break;
            case UPDATE:
                System.out.println("Nhập ID đơn hàng bạn muốn chỉnh sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập ID đơn hàng bạn muốn xóa: ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = orderService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("Không tìm thấy, vui lòng nhập lại!");
            }
        } while (isTrue);
        return id;
    }

    public void deleteOrder() {
        boolean isRetry = true;
        do {
            showOrder(orderService.findAll(), InputOption.DELETE);
            long id = inputId(InputOption.DELETE);
            int option;
            boolean isTrue = true;
            do {
                try {
                    AppUtils.menuDelete();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            returnProductQuantityAfterDeleteOrder(id);
                            System.out.println("Xóa hóa đơn thành công!");
                            AppUtils.pressAnyKeyToContinue();
                            isTrue = false;
                            break;
                        case 2:
                            isTrue = false;
                            break;
                        case 0:
                            System.out.println("Exit the program...");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                            System.out.print(" => ");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Sai cú pháp. Vui lòng nhập lại!");
                }
            } while (isTrue);
        } while (isRetry == AppUtils.isRetry(InputOption.DELETE));
    }

    public void findOrder(long userId) {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuFindOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findByOrderId(userId);
                        break;
                    case 2:
                        findByFullName(userId);
                        break;
                    case 3:
                        findByPhone(userId);
                        break;
                    case 4:
                        findByAddress(userId);
                        break;
                    case 5:
                        findByUserId();
                        break;
                    case 6:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    public void memberFindOrder(long userId) {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuMemberFindOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findByOrderId(userId);
                        break;
                    case 2:
                        findByFullName(userId);
                        break;
                    case 3:
                        findByPhone(userId);
                        break;
                    case 4:
                        findByAddress(userId);
                        break;
                    case 5:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
    }

    private void findByUserId() {
        showOrder(orderService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░ TÌM KIẾM THEO NHÂN VIÊN ░░░░░░");
        System.out.print("Nhập id nhân viên: ");
        long value = Long.parseLong(scanner.nextLine());
        List<Order> ordersFind = orderService.findByUserId(value);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
        AppUtils.pressAnyKeyToContinue();
    }

    private void findByAddress(long userId) {
        User user = userService.findById(userId);
        if (user.getRole() == Role.ADMIN) {
            showOrder(orderService.findAll(), InputOption.FIND);
        }
        if (user.getRole() == Role.USER) {
            showOrder(orderService.findOrderByUserId(userId), InputOption.FIND);
        }
        System.out.println("░░░░░░░ TÌM KIẾM THEO ĐỊA CHỈ ░░░░░░░");
        System.out.print("Nhập địa chỉ cần tìm: ");
        String value = scanner.nextLine();
        List<Order> ordersFind = orderService.findByAddress(value, userId);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
        AppUtils.pressAnyKeyToContinue();
    }

    private void findByPhone(long userId) {
        User user = userService.findById(userId);
        if (user.getRole() == Role.ADMIN) {
            showOrder(orderService.findAll(), InputOption.FIND);
        }
        if (user.getRole() == Role.USER) {
            showOrder(orderService.findOrderByUserId(userId), InputOption.FIND);
        }
        System.out.println("░░░░░ TÌM KIẾM THEO SỐ ĐIỆN THOẠI ░░░░░");
        System.out.print("Nhập số điện thoại cần tìm: ");
        String value = scanner.nextLine();
        List<Order> ordersFind = orderService.findByPhone(value, userId);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy");
        }
        AppUtils.pressAnyKeyToContinue();
    }

    private void findByFullName(long userId) {
        User user = userService.findById(userId);
        if (user.getRole() == Role.ADMIN) {
            showOrder(orderService.findAll(), InputOption.FIND);
        }
        if (user.getRole() == Role.USER) {
            showOrder(orderService.findOrderByUserId(userId), InputOption.FIND);
        }
        System.out.println("░░░░░ TÌM KIẾM THEO KHÁCH HÀNG ░░░░░");
        System.out.print("Nhập tên khách hàng: ");
        String value = scanner.nextLine();
        List<Order> ordersFind = orderService.findByFullName(value, userId);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
        AppUtils.pressAnyKeyToContinue();
    }

    private void findByOrderId(long userId) {
        User user = userService.findById(userId);
        if (user.getRole() == Role.ADMIN) {
            showOrder(orderService.findAll(), InputOption.FIND);
        }
        if (user.getRole() == Role.USER) {
            showOrder(orderService.findOrderByUserId(userId), InputOption.FIND);
        }
        System.out.println("░░░░░░░░░░ TÌM KIẾM THEO ID ░░░░░░░░░░");
        System.out.print("Nhập id hóa đơn cần tìm: ");
        long value = Long.parseLong(scanner.nextLine());
        Order order = orderService.findById(value);
        if (order != null) {
            List<Order> ordersFind = new ArrayList<>();
            ordersFind.add(order);
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
        AppUtils.pressAnyKeyToContinue();
    }

    private static void menuFindOrder() {
        System.out.println("░░░░░░░░░░░░░░░ TÌM KIẾM ░░░░░░░░░░░░░░");
        System.out.println("░                                     ░");
        System.out.println("░   1. Tìm kiếm theo Id.              ░");
        System.out.println("░   2. Tìm kiếm theo khách hàng.      ░");
        System.out.println("░   3. Tìm kiếm theo số điện thoại.   ░");
        System.out.println("░   4. Tìm kiếm theo địa chỉ.         ░");
        System.out.println("░   5. Tìm kiếm theo nhân viên.       ░");
        System.out.println("░   6. Trở lại.                       ░");
        System.out.println("░   0. Thoát.                         ░");
        System.out.println("░                                     ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private static void menuMemberFindOrder() {
        System.out.println("░░░░░░░░░░░░░░░ TÌM KIẾM ░░░░░░░░░░░░░░");
        System.out.println("░                                     ░");
        System.out.println("░   1. Tìm kiếm theo Id.              ░");
        System.out.println("░   2. Tìm kiếm theo khách hàng.      ░");
        System.out.println("░   3. Tìm kiếm theo số điện thoại.   ░");
        System.out.println("░   4. Tìm kiếm theo địa chỉ.         ░");
        System.out.println("░   5. Trở lại.                       ░");
        System.out.println("░   0. Thoát.                         ░");
        System.out.println("░                                     ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private void menuUpdateOrder() {
        System.out.println("░░░░░░░░ CHỈNH SỬA ĐƠN HÀNG ░░░░░░░░");
        System.out.println("░                                  ░");
        System.out.println("░    1. Chỉnh sửa tên khách hàng.  ░");
        System.out.println("░    2. Chỉnh sửa số điện thoại.   ░");
        System.out.println("░    3. Chỉnh sửa địa chỉ.         ░");
        System.out.println("░    4. Chỉnh sửa giỏ hàng.        ░");
        System.out.println("░    5. Trở lại.                   ░");
        System.out.println("░    0. Thoát.                     ░");
        System.out.println("░                                  ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    public void sortOrder(long userId) {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuSortOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        sortById(userId);
                        break;
                    case 2:
                        sortByFullName(userId);
                        break;
                    case 3:
                        sortByPhone(userId);
                        break;
                    case 4:
                        sortByAddress(userId);
                        break;
                    case 5:
                        sortByGrandTotal(userId);
                        break;
                    case 6:
                        sortByUserId();
                        break;
                    case 7:
                        sortByCreateTime(userId);
                        break;
                    case 8:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    public void memberSortOrder(long userId) {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuMemberSortOrder();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        sortById(userId);
                        break;
                    case 2:
                        sortByFullName(userId);
                        break;
                    case 3:
                        sortByPhone(userId);
                        break;
                    case 4:
                        sortByAddress(userId);
                        break;
                    case 5:
                        sortByGrandTotal(userId);
                        break;
                    case 6:
                        sortByCreateTime(userId);
                        break;
                    case 7:
                        isTrue = false;
                        break;
                    case 0:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    private void sortByCreateTime(long userId) {
        System.out.println("░░░░░ SẮP XẾP THEO THỜI GIAN TẠO ĐƠN HÀNG ░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByCreatedAt(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByCreatedAt(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByCreatedAt(TypeSort.DESC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByCreatedAt(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortByUserId() {
        System.out.println("░░░░░░ SẮP XẾP THEO NHÂN VIÊN ░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showOrder(orderService.sortByUserId(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showOrder(orderService.sortByUserId(TypeSort.DESC), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortByGrandTotal(long userId) {
        System.out.println("░░░░░░░░ SẮP XẾP THEO TỔNG TIỀN ░░░░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByGrandTotal(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByGrandTotal(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByGrandTotal(TypeSort.DESC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByGrandTotal(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortByAddress(long userId) {
        System.out.println("░░░░░░░ SẮP XẾP THEO ĐỊA CHỈ ░░░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByAddress(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByAddress(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByAddress(TypeSort.DESC), InputOption.SORT);

                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByAddress(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortByPhone(long userId) {
        System.out.println("░░░░░░ SẮP XẾP THEO SỐ ĐIỆN THOẠI ░░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByPhone(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByPhone(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByPhone(TypeSort.DESC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByPhone(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortByFullName(long userId) {
        System.out.println("░░░░░░░░ SẮP XẾP THEO TÊN KHÁCH HÀNG ░░░░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByFullName(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByFullName(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortByFullName(TypeSort.DESC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortByFullName(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void sortById(long userId) {
        System.out.println("░░░░░░░░░░ SẮP XẾP THEO ID ░░░░░░░░░░");
        User user = userService.findById(userId);
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortById(TypeSort.ASC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortById(TypeSort.ASC, userId), InputOption.SORT);
                    break;
                case 2:
                    if (user.getRole() == Role.ADMIN)
                        showOrder(orderService.sortById(TypeSort.DESC), InputOption.SORT);
                    if (user.getRole() == Role.USER)
                        showOrder(orderService.sortById(TypeSort.DESC, userId), InputOption.SORT);
                    break;
                default:
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Sai cú pháp. Vui lòng nhập lại!");
        }
    }

    private void menuSortOrder() {
        System.out.println("░░░░░░░░░░░ SẮP XẾP HÓA ĐƠN ░░░░░░░░░░");
        System.out.println("░                                    ░");
        System.out.println("░    1. Sắp xếp theo ID.             ░");
        System.out.println("░    2. Sắp xếp theo khách hàng.     ░");
        System.out.println("░    3. Sắp xếp theo số điện thoại.  ░");
        System.out.println("░    4. Sắp xếp theo địa chỉ.        ░");
        System.out.println("░    5. Sắp xếp theo đơn giá.        ░");
        System.out.println("░    6. Sắp xếp theo nhân viên.      ░");
        System.out.println("░    7. Sắp xếp theo thời gian tạo.  ░");
        System.out.println("░    8. Trở lại.                     ░");
        System.out.println("░    0. Thoát.                       ░");
        System.out.println("░                                    ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private void menuMemberSortOrder() {
        System.out.println("░░░░░░░░░░░ SẮP XẾP HÓA ĐƠN ░░░░░░░░░░");
        System.out.println("░                                    ░");
        System.out.println("░    1. Sắp xếp theo ID.             ░");
        System.out.println("░    2. Sắp xếp theo khách hàng.     ░");
        System.out.println("░    3. Sắp xếp theo số điện thoại.  ░");
        System.out.println("░    4. Sắp xếp theo địa chỉ.        ░");
        System.out.println("░    5. Sắp xếp theo đơn giá.        ░");
        System.out.println("░    6. Sắp xếp theo thời gian tạo.  ░");
        System.out.println("░    7. Trở lại.                     ░");
        System.out.println("░    0. Thoát.                       ░");
        System.out.println("░                                    ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    public boolean isRetryOrder(long orderId) {
        do {
            System.out.println("===> Chọn 'y': tiếp tục thêm đơn hàng \t|\t 'q': trở lại \t|\t 'h': hủy đơn \t|\t't': thoát.");
            System.out.print(" => ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    return true;
                case "q":
                    return false;
                case "h":
                    System.out.println("Hủy đơn hàng thành công!");
                    returnProductQuantityAfterDeleteOrder(orderId);
                    return false;
                case "t":
                    returnProductQuantityAfterDeleteOrder(orderId);
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

    public void returnProductQuantityAfterDeleteOrder(long orderId) {
        try {
            List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                orderItemView.deleteOrderItemById(orderItem.getId());
            }
            orderService.delete(orderId);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<Order> findAllByUserId(long userId) {
        return orderService.findOrderByUserId(userId);
    }

    private long inputIdDeleted() {
        long id;
        System.out.print("Nhập ID hóa đơn cần khôi phục: ");
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = orderService.existByIdDeleted(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("Không tìm thấy, vui lòng nhập lại!");
            }
        } while (isTrue);
        return id;
    }

    public void restoreOrder() {
        List<Order> ordersDelete = orderService.findAllDelete();
        if (ordersDelete != null) {
            showOrder(ordersDelete, InputOption.DELETE);
            boolean isTrue = true;
            do {
                System.out.println("Chọn 'y' để chọn đơn hàng cần khôi phục \t|\t 'q' để trở lại.");
                System.out.print(" => ");
                String option = scanner.nextLine();
                switch (option) {
                    case "y":
                        long id = inputIdDeleted();
                        Order orderDeleted = orderService.findByIdDeleted(id);
                        orderItemView.restoreOrderItem(id);
                        orderService.add(orderDeleted);
                        orderService.deleteInFileDeleted(id);
                        System.out.printf("Khôi phục đơn hàng '%s' của khách hàng %s thành công!\n", id, orderDeleted.getFullName());
                        AppUtils.pressAnyKeyToContinue();
                        showOrder(orderService.findAllDelete(), InputOption.DELETE);
                        break;
                    case "q":
                        isTrue = false;
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } while (isTrue);
        } else {
            System.out.println("Không có đơn hàng cần khôi phục!");
            AppUtils.pressAnyKeyToContinue();
        }
    }
}