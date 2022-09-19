package vn.ltt.shop.view;


import vn.ltt.shop.model.User;
import vn.ltt.shop.model.Role;
import vn.ltt.shop.service.IUserService;
import vn.ltt.shop.service.UserService;
import vn.ltt.shop.utils.AppUtils;

import java.util.Scanner;

public class LoginView {
    private final static Scanner scanner = new Scanner(System.in);
    private static final int EXIT_PROGRAM = 0;
    private static final int LOGIN = 1;
    private static final int FORGET_PASSWORD = 2;
    private static final int RESEND_OTP = 1;
    private static final int RETURN = 2;

    private final IUserService userService;

    private static final UserView userview = new UserView();
    private static User user = new User();

    public LoginView() {
        userService = UserService.getInstance();
    }

    public void login(Role role) {
        int option = -1;
        do {
            menuLogin();
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case LOGIN:
                        loginProgram(role);
                        break;
                    case FORGET_PASSWORD:
                        forgetPassWord(role);
                        break;
                    case EXIT_PROGRAM:
                        System.out.println("Exit the program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (option != EXIT_PROGRAM);
    }

    private void forgetPassWord(Role role) {
        String userName, passWord, email, phone;
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
            if (checkOTP()) {
                System.out.print("Nhập mật khẩu mới: ");
                passWord = userview.inputPassWord();
                user.setPassWord(passWord);
                userService.update(user);
                System.out.println("Cập nhật lại mật khẩu thành công. Vui lòng đăng nhập lại!\n");
            }
        }
    }

    private boolean checkOTP() {
        int option;
        do {
            String OTP = AppUtils.randomString(6);
            System.out.printf("- Mã xác thực của bạn là: %s. Vui lòng không cung cấp mã OTP cho bất kỳ ai để bảo vệ tài khoản!\n", OTP);
            System.out.print("Nhập mã OTP: ");
            String myOTP = scanner.nextLine();
            if (OTP.equals(myOTP)) {
                return true;
            } else {
                try {
                    System.out.println("-  " +
                            "Mã OTP sai!");
                    menuOTP();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case RESEND_OTP:
                            break;
                        case RETURN:
                            return false;
                        case EXIT_PROGRAM:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                            System.out.print("=> ");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Lựa chọn sai. Vui lòng nhập lại!");
                    System.out.print("=> ");
                }
            }
        } while (true);
    }

    private void loginProgram(Role role) {
        String userName, passWord;
        System.out.println("░░░░░░░░░░░░ ĐĂNG NHẬP ░░░░░░░░░░░░");
        System.out.print("Tài khoản: ");
        userName = AppUtils.retryString();
        System.out.print("Mật khẩu:  ");
        passWord = AppUtils.retryString();
        user = userService.login(userName, passWord, role);
        long userId = user.getId();
        if (user == null) {
            System.out.println("Tài khoản hoặc mật khẩu không đúng!");
            AppUtils.pressAnyKeyToContinue();
        } else {
            System.out.println("\n    ===> ĐĂNG NHẬP THÀNH CÔNG <===");
            System.out.printf("===> XIN CHÀO %s: %s <===\n\n", user.getRole(), user.getFullName().toUpperCase());
            AppUtils.pressAnyKeyToContinue();
            if (role == Role.ADMIN) {
                AdminView.launch(userId);
            } else {
                MemberView.launch(userId);
            }
        }
    }

    private static void menuOTP() {
        System.out.println("░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░");
        System.out.println("░       1. Gửi lại mã OTP.        ░");
        System.out.println("░       2. Trở lại menu chính.    ░");
        System.out.println("░       0. Thoát.                 ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }

    private static void menuLogin() {
        System.out.println("░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░");
        System.out.println("░        1. Đăng nhập.            ░");
        System.out.println("░        2. Quên mật khẩu.        ░");
        System.out.println("░        0. Thoát.                ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}