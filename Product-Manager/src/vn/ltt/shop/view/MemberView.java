package vn.ltt.shop.view;

import vn.ltt.shop.model.User;
import vn.ltt.shop.service.IUserService;
import vn.ltt.shop.service.UserService;
import vn.ltt.shop.utils.AppUtils;

import java.util.Scanner;

public class MemberView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IUserService userService = UserService.getInstance();

    private static final UserView userView = new UserView();

    public static void launch(long userId) {
        boolean isTrue = true;
        do {
            try {
                menuMemberManager();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        settingAccount(userId);
                        break;
                    case 2:
                        ProductViewLauncher.memberLaunch();
                        break;
                    case 3:
                        OrderViewLauncher.memberLaunch(userId);
                        break;
                    case 4:
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

    private static void settingAccount(long id) {
        User user = userService.findById(id);
        boolean isTrue = true;
        do {
            try {
                menuSettingAccount();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        showInfoAccount(user);
                        break;
                    case 2:
                        changePassWord(user);
                        break;
                    case 3:
                        changeFullName(user);
                        break;
                    case 4:
                        changeEmail(user);
                        break;
                    case 5:
                        changePhone(user);
                        break;
                    case 6:
                        changeAddress(user);
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

    private static void changeAddress(User user) {
        userView.updateAddress(user);
    }

    private static void changePhone(User user) {
        userView.updatePhone(user);
    }

    private static void changeEmail(User user) {
        userView.updateEmail(user);
    }

    private static void changeFullName(User user) {
        userView.updateFullName(user);
    }

    private static void showInfoAccount(User user) {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ THÔNG TIN TÀI KHOẢN ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s%-9s | %-3s%-14s | %-9s%-17s | %-14s%-19s | %-5s%-9s | %-4s%-11s |\n",
                "", "ID",
                "", "TÀI KHOẢN",
                "", "HỌ TÊN",
                "", "EMAIL",
                "", "SĐT",
                "", "ĐỊA CHỈ"
        );
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s%-12s | %-2s%-15s | %-2s%-24s | %-3s%-30s | %-2s%-12s | %-2s%-13s |\n",
                "", user.getId(),
                "", user.getUserName(),
                "", user.getFullName(),
                "", user.getEmail(),
                "", user.getPhone(),
                "", user.getAddress()
        );
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        AppUtils.pressAnyKeyToContinue();
    }

    private static void changePassWord(User user) {
        String passWord, rePassWord;
        do {
            System.out.print("Nhập mật khẩu mới: ");
            passWord = userView.inputPassWord();
            System.out.print("Nhập lại mật khẩu: ");
            rePassWord = userView.inputPassWord();
            if (passWord.equals(rePassWord)) {
                user.setPassWord(passWord);
                userService.update(user);
                System.out.println("Thay đổi mật khẩu thành công.");
            } else {
                System.out.println("Mật khẩu không trùng nhau!");
            }
            AppUtils.pressAnyKeyToContinue();
        } while (!passWord.equals(rePassWord));
    }

    private static void menuMemberManager() {
        System.out.println("░░░░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░░░░");
        System.out.println("░                                       ░");
        System.out.println("░          1. Cài đặt tài khoản.        ░");
        System.out.println("░          2. Quản lý sản phẩm.         ░");
        System.out.println("░          3. Quản lý đơn hàng.         ░");
        System.out.println("░          4. Đăng xuất.                ░");
        System.out.println("░          0. Thoát.                    ░");
        System.out.println("░                                       ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }

    private static void menuSettingAccount() {
        System.out.println("░░░░░░░░░░░░░░░░░░ MENU ░░░░░░░░░░░░░░░░░");
        System.out.println("░                                       ░");
        System.out.println("░      1. Hiện thông tin tài khoản.     ░");
        System.out.println("░      2. Thay đổi mật khẩu.            ░");
        System.out.println("░      3. Thay đổi họ tên.              ░");
        System.out.println("░      4. Thay đổi email.               ░");
        System.out.println("░      5. Thay đổi số điện thoại.       ░");
        System.out.println("░      6. Thay đổi địa chỉ.             ░");
        System.out.println("░      7. Trở lại.                      ░");
        System.out.println("░      0. Thoát.                        ░");
        System.out.println("░                                       ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}