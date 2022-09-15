package vn.ltt.shop.view;

import vn.ltt.shop.model.User;
import vn.ltt.shop.model.Role;
import vn.ltt.shop.service.IUserService;
import vn.ltt.shop.service.UserService;
import vn.ltt.shop.utils.AppUtils;

import java.util.Scanner;

public class LoginView {
    private final static Scanner scanner = new Scanner(System.in);
    private final IUserService userService;

    private static final UserView userview = new UserView();
    User user = new User();

    public LoginView() {
        userService = UserService.getInstance();
    }

    public void login(Role role) {
        String userName, passWord, email, phone;
        int option = -1;
        do {
            menuLogin();
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        System.out.println("░░░░░░░░░░░░ ĐĂNG NHẬP ░░░░░░░░░░░░");
                        System.out.print("Tài khoản: ");
                        userName = AppUtils.retryString();
                        System.out.print("Mật khẩu:  ");
                        passWord = AppUtils.retryString();
                        user = userService.login(userName, passWord, role);
                        if (user == null) {
                            System.out.println("Tài khoản hoặc mật khẩu không đúng!");
                            AppUtils.pressAnyKeyToContinue();
                        } else {
                            System.out.println("\n    ===> ĐĂNG NHẬP THÀNH CÔNG <===");
                            System.out.printf("===> XIN CHÀO %s: %s <===\n\n", user.getRole(), user.getFullName().toUpperCase());
                            AppUtils.pressAnyKeyToContinue();
                            if (role == Role.ADMIN) {
                                AdminView.launch(user.getId());
                            } else {
                                MemberView.launch(user.getId());
                            }
                        }
                        break;
                    case 2:
                        System.out.println("░░░░░░░░░░ QUÊN MẬT KHẨU ░░░░░░░░░░");
                        System.out.print("Nhập tài khoản: ");
                        userName = AppUtils.retryString();
                        System.out.print("Nhập email: ");
                        email = AppUtils.retryString();
                        System.out.print("Nhập số điện thoại: ");
                        phone = AppUtils.retryString();
                        user = userService.passwordRetrieval(userName, email, phone, role);
                        if (user == null) {
                            System.out.println("Tài khoản hoặc email hoặc số điện thoại không đúng!");
                            AppUtils.pressAnyKeyToContinue();
                        } else {
                            System.out.print("Nhập mật khẩu mới: ");
                            passWord = userview.inputPassWord();
                            user.setPassWord(passWord);
                            userService.update(user);
                            System.out.println("Cập nhật lại mật khẩu thành công. Vui lòng đăng nhập lại!");
                        }
                        break;
                    case 3:
                        System.out.println("Exit the program...");
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (option != 3);
    }

    private static void menuLogin() {
        System.out.println("░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░");
        System.out.println("░        1. Đăng nhập.            ░");
        System.out.println("░        2. Quên mật khẩu.        ░");
        System.out.println("░        3. Thoát.                ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}