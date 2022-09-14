package vn.ltt.shop.view;

import java.util.Scanner;

public class AdminView {

    private static final int USER_MANAGER = 1;
    private static final int PRODUCT_MANAGER = 2;
    private static final int ORDER_MANAGER = 3;
    private static final int LOGOUT = 5;
    private static final int EXIT = 6;
    private static final int STATISTICAL_ORDER = 4;

    public static void launch(long userId) {
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = true;
        do {
            try {
                menuAdminOption();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case USER_MANAGER:
                        UserViewLauncher.launch();
                        break;
                    case PRODUCT_MANAGER:
                        ProductViewLauncher.launch();
                        break;
                    case ORDER_MANAGER:
                        OrderViewLauncher.launch(userId);
                        break;
                    case STATISTICAL_ORDER:
                        OrderViewLauncher.statistical();
                        break;
                    case LOGOUT:
                        isTrue = false;
                        break;
                    case EXIT:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Chọn sai. Vui lòng nhập lại!");
                        System.out.print(" => ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sai cú pháp, vui lòng nhập lại!");
            }
        } while (isTrue);
    }

    private static void menuAdminOption() {
        System.out.println("░░░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░░░░");
        System.out.println("░                                      ░");
        System.out.println("░         1. Quản lý tài khoản.        ░");
        System.out.println("░         2. Quản lý sản phẩm.         ░");
        System.out.println("░         3. Quản lý đơn hàng.         ░");
        System.out.println("░         4. Thống kê.                 ░");
        System.out.println("░         5. Đăng xuất.                ░");
        System.out.println("░         0. Thoát.                    ░");
        System.out.println("░                                      ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}