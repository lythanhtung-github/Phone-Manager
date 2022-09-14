package vn.ltt.shop.view;

import java.util.Scanner;

public class AdminView {

    public static void launch(long userId) {
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = true;
        do {
            try {
                menuAdminOption();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        UserViewLauncher.launch();
                        break;
                    case 2:
                        ProductViewLauncher.launch();
                        break;
                    case 3:
                       OrderViewLauncher.launch(userId);
                        break;
                    case 4:
                        isTrue = false;
                        break;
                    case 0:
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
        System.out.println("░         4. Đăng xuất.                ░");
        System.out.println("░         0. Thoát.                    ░");
        System.out.println("░                                      ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}