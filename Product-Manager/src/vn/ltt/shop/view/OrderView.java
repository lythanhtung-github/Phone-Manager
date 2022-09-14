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
    UserView userView = new UserView();
    OrderItemView orderItemView = new OrderItemView();

    public OrderView() {
        orderService = OrderService.getInstance();
        orderItemService = OrderItemService.getInstance();
        userService = UserService.getInstance();
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
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressAnyKeyToContinue();
        }
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
                System.out.println("Nhập địa chỉ (Ký tự đầu của từng từ phải viết hoa, VD: Hue)");
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

    public void updateOrder() {
        int option;
        boolean isTrue = true;
        do {
            try {
                showOrder(orderService.findAll(), InputOption.UPDATE);
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
            case UPDATE:
                System.out.println("Nhập Id hóa đơn bạn muốn chỉnh sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập Id hóa đơn bạn muốn xóa: ");
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
                    menuDeleteOrder();
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
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
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
        System.out.print("Nhập id người tạo đơn: ");
        long value = Long.parseLong(scanner.nextLine());
        List<Order> ordersFind = orderService.findByUserId(value);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
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
        List<Order> ordersFind = orderService.findByAddress(value);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
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
        List<Order> ordersFind = orderService.findByPhone(value);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy");
        }
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
        List<Order> ordersFind = orderService.findByFullName(value);
        if (ordersFind != null) {
            showOrder(ordersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
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
        System.out.println("Enter your choice: ");
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
        System.out.println("Enter your choice: ");
        System.out.print(" => ");
    }

    private static void menuDeleteOrder() {
        System.out.println("░░░░░ BẠN CÓ MUỐN XÓA KHÔNG? ░░░░░");
        System.out.println("░            1. Có.              ░");
        System.out.println("░            2. Không.           ░");
        System.out.println("░            0. Thoát.           ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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
        menuSortASCOrDESC();
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

    private static void menuSortASCOrDESC() {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░         1. Tăng dần.        ░");
        System.out.println("░         2. Giảm dần.        ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private void menuSortOrder() {
        System.out.println("░░░░░░░░░░░ SẮP XẾP HÓA ĐƠN ░░░░░░░░░░");
        System.out.println("░                                    ░");
        System.out.println("░    1. Sắp xếp theo Id.             ░");
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
        System.out.println("░    1. Sắp xếp theo Id.             ░");
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
}