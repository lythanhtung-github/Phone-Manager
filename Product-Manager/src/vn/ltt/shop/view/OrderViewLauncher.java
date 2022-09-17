package vn.ltt.shop.view;

import vn.ltt.shop.model.Order;
import vn.ltt.shop.service.OrderService;
import vn.ltt.shop.utils.AppUtils;

import java.util.List;
import java.util.Scanner;

public class OrderViewLauncher {
    private static final int SHOW = 1;
    private static final int ADD = 2;
    private static final int UPDATE = 3;
    private static final int DELETE = 4;
    private static final int FIND = 5;
    private static final int SORT = 6;
    private static final int RETURN = 8;
    private static final int EXIT = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final int FIND_ORDER = 4;
    private static final int SORT_ORDER = 5;
    private static final int STATISTICAL_BY_DAY = 1;
    private static final int STATISTICAL_BY_MONTH = 2;
    private static final int STATISTICAL_BY_YEAR = 3;
    private static final int RESTORE = 7;
    private static final OrderView orderView = new OrderView();


    public OrderViewLauncher() {
    }

    public static void launch(long userId) {
        boolean isTrue = true;
        do {
            menuOrderManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case SHOW:
                        orderView.showOrder(OrderService.getInstance().findAll(), InputOption.SHOW);
                        break;
                    case ADD:
                        orderView.addOrder(userId);
                        break;
                    case UPDATE:
                        orderView.updateOrder(userId);
                        break;
                    case DELETE:
                        orderView.deleteOrder();
                        break;
                    case FIND:
                        orderView.findOrder(userId);
                        break;
                    case SORT:
                        orderView.sortOrder(userId);
                        break;
                    case RESTORE:
                        orderView.restoreOrder();
                        break;
                    case RETURN:
                        isTrue = false;
                        break;
                    case EXIT:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" => ");
            }
        } while (isTrue);
    }

    public static void memberLaunch(long userId) {
        boolean isTrue = true;
        do {
            menuMemberOrderManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case SHOW: {
                        List<Order> orders = orderView.findAllByUserId(userId);
                        if (orders.isEmpty()) {
                            System.out.println("Bạn chưa có đơn hàng nào!");
                            AppUtils.pressAnyKeyToContinue();
                            break;
                        }
                        orderView.showOrder(orders, InputOption.SHOW);
                        break;
                    }
                    case ADD:
                        orderView.addOrder(userId);
                        break;
                    case UPDATE:
                        orderView.updateOrder(userId);
                        break;
                    case FIND_ORDER:
                        orderView.memberFindOrder(userId);
                        break;
                    case SORT_ORDER:
                        orderView.memberSortOrder(userId);
                        break;
                    case RETURN:
                        isTrue = false;
                        break;
                    case EXIT:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" => ");
            }
        } while (isTrue);
    }

    public static void menuOrderManager() {
        System.out.println("░░░░░░░░░░░░░░░ MENU ORDER ░░░░░░░░░░░░░░");
        System.out.println("░                                       ░");
        System.out.println("░       1. Hiện danh sách đơn hàng.     ░");
        System.out.println("░       2. Thêm đơn hàng.               ░");
        System.out.println("░       3. Chỉnh sửa đơn hàng.          ░");
        System.out.println("░       4. Xóa đơn hàng.                ░");
        System.out.println("░       5. Tìm kiếm đơn hàng.           ░");
        System.out.println("░       6. Sắp xếp đơn hàng.            ░");
        System.out.println("░       7. Khôi phục đơn hàng.          ░");
        System.out.println("░       8. Trở lại.                     ░");
        System.out.println("░       0. Thoát.                       ░");
        System.out.println("░                                       ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }

    public static void menuMemberOrderManager() {
        System.out.println("░░░░░░░░░░░░░░░ MENU ORDER ░░░░░░░░░░░░░░");
        System.out.println("░                                       ░");
        System.out.println("░       1. Hiện danh sách đơn hàng.     ░");
        System.out.println("░       2. Thêm đơn hàng.               ░");
        System.out.println("░       3. Chỉnh sửa đơn hàng.          ░");
        System.out.println("░       4. Tìm kiếm đơn hàng.           ░");
        System.out.println("░       5. Sắp xếp đơn hàng.            ░");
        System.out.println("░       8. Trở lại.                     ░");
        System.out.println("░       0. Thoát.                       ░");
        System.out.println("░                                       ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }

    public static void menuStatisticalManager() {
        System.out.println("░░░░░░░░░░░░░░░ THỐNG KÊ ░░░░░░░░░░░░░░");
        System.out.println("░                                     ░");
        System.out.println("░       1. Thống kê theo ngày.        ░");
        System.out.println("░       2. Thống kê theo tháng.       ░");
        System.out.println("░       3. Thống kê theo năm.         ░");
        System.out.println("░       8. Trở lại.                   ░");
        System.out.println("░       0. Thoát.                     ░");
        System.out.println("░                                     ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }


    public static void statistical() {
        do {
            menuStatisticalManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case STATISTICAL_BY_DAY:
                        orderView.statisticalByDay();
                        break;
                    case STATISTICAL_BY_MONTH:
                        orderView.statisticalByMonth();
                        break;
                    case STATISTICAL_BY_YEAR:
                        orderView.statisticalByYear();
                        break;
                    case RETURN:
                        break;
                    case EXIT:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
                System.out.print(" => ");
            }
        } while (AppUtils.isRetry(InputOption.STATISTICAL));
    }
}