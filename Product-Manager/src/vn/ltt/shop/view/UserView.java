package vn.ltt.shop.view;

import vn.ltt.shop.model.Role;
import vn.ltt.shop.model.User;
import vn.ltt.shop.service.IUserService;
import vn.ltt.shop.service.UserService;
import vn.ltt.shop.utils.AppUtils;
import vn.ltt.shop.utils.InstantUtils;
import vn.ltt.shop.utils.TypeSort;
import vn.ltt.shop.utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private final IUserService userService;
    private static final Scanner scanner = new Scanner(System.in);

    public UserView() {
        userService = UserService.getInstance();
    }

    public void showUser(List<User> users, InputOption option) {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ DANH SÁCH TÀI KHOẢN ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s%-9s | %-5s%-12s | %-6s%-14s | %-13s%-20s | %-6s%-8s | %-2s%-22s | %-1s%-6s | %-4s%-18s | %-2s%-20s |\n",
                "", "ID",
                "", "TÀI KHOẢN",
                "", "HỌ TÊN",
                "", "EMAIL",
                "", "SĐT",
                "", "ĐỊA CHỈ",
                "", "QUYỀN",
                "", "THỜI GIAN TẠO",
                "", "THỜI GIAN CHỈNH SỬA"
        );
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (User user : users) {
            System.out.printf("| %-2s%-12s | %-2s%-15s | %-1s%-19s | %-3s%-30s | %-2s%-12s | %-2s%-22s | %-1s%-6s | %-2s%-20s | %-2s%-20s |\n",
                    "", user.getId(),
                    "", user.getUserName(),
                    "", user.getFullName(),
                    "", user.getEmail(),
                    "", user.getPhone(),
                    "", user.getAddress(),
                    "", user.getRole(),
                    "", InstantUtils.instantToString(user.getCreatedAt()),
                    "", user.getUpdatedAt() == null ? "" : InstantUtils.instantToString(user.getUpdatedAt())
            );
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        if (option != InputOption.UPDATE && option != InputOption.DELETE && option != InputOption.FIND) {
            AppUtils.pressAnyKeyToContinue();
        }
    }

    public void addUser() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String username = inputUserName();
                String password = inputPassWord();
                String fullName = inputFullName(InputOption.ADD);
                String email = inputEmail(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                User user = new User(id, username, password, fullName, email, phone, address, Role.USER);
                setRole(user);
                userService.add(user);
                System.out.println("Thêm tài khoản " + user.getUserName() + " thành công!");
                AppUtils.pressAnyKeyToContinue();
            } catch (Exception e) {
                System.out.println("Sai cú pháp. Vui lòng nhập lại!");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void updateUser() {
        int option;
        boolean isTrue = true;
        do {
            try {
                showUser(userService.findAll(), InputOption.UPDATE);
                long id = inputId(InputOption.UPDATE);
                User newUser = userService.findById(id);
                menuUpdateUser();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        updateFullName(newUser);
                        break;
                    case 2:
                        updateEmail(newUser);
                        break;
                    case 3:
                        updatePhone(newUser);
                        break;
                    case 4:
                        updateAddress(newUser);
                        break;
                    case 5:
                        setRole(newUser);
                        updateRole(newUser);
                        break;
                    case 6:
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

    public void deleteUser() {
        boolean isRetry = true;
        do {
            showUser(userService.findAll(), InputOption.DELETE);
            long id = inputId(InputOption.DELETE);
            User user = userService.findById(id);
            int option;
            boolean isTrue = true;
            do {
                try {
                    AppUtils.menuDelete();
                    option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            if (user.getRole() == Role.ADMIN) {
                                System.out.println("Không thể xóa tài khoản Admin!");
                            } else {
                                System.out.println("Xóa tài khoản " + user.getUserName() + " thành công!");
                                userService.deleteById(id);
                            }
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
                            System.out.println("Lựa chọn sai. Vui lòng nhập!");
                            System.out.print(" => ");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Sai cú pháp. Vui lòng nhập lại!");
                }
            } while (isTrue);
        } while (isRetry == AppUtils.isRetry(InputOption.DELETE));
    }

    public void sortUser() {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuSortUser();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        sortById();
                        break;
                    case 2:
                        sortByUserName();
                        break;
                    case 3:
                        sortByFullName();
                        break;
                    case 4:
                        sortByEmail();
                        break;
                    case 5:
                        sortByPhone();
                        break;
                    case 6:
                        sortByRole();
                        break;
                    case 7:
                        sortByAddress();
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

    public void findUser() {
        int option;
        boolean isTrue = true;
        do {
            try {
                menuFindUser();
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        findById();
                        break;
                    case 2:
                        findByUserName();
                        break;
                    case 3:
                        findByFullName();
                        break;
                    case 4:
                        findByEmail();
                        break;
                    case 5:
                        findByPhone();
                        break;
                    case 6:
                        findByAddress();
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
        } while (isTrue == AppUtils.isRetry(InputOption.FIND));
    }

    private void findById() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░░░ TÌM KIẾM THEO ID ░░░░░░░░");
        System.out.print("Nhập id: ");
        long value = Long.parseLong(scanner.nextLine());
        User user = userService.findById(value);
        if (user != null) {
            List<User> usersFind = new ArrayList<>();
            usersFind.add(user);
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void findByAddress() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░ TÌM KIẾM THEO ĐỊA CHỈ ░░░░░░");
        System.out.print("Nhập địa chỉ: ");
        String value = scanner.nextLine();
        List<User> usersFind = userService.findByAddress(value);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void findByPhone() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░ TÌM KIẾM BẰNG SĐT ░░░░░░░");
        System.out.print("Enter phone: ");
        String value = scanner.nextLine();
        List<User> usersFind = userService.findByPhone(value);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void findByEmail() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░░░ TÌM KIẾM BẰNG EMAIL ░░░░░░░░░");
        System.out.print("Nhập email: ");
        String value = scanner.nextLine();
        List<User> usersFind = userService.findByEmail(value);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void findByFullName() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░ TÌM KIẾM BẰNG TÊN ░░░░░░░");
        System.out.print("Nhập tên người dùng: ");
        String value = scanner.nextLine();
        List<User> usersFind = userService.findByFullName(value);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void findByUserName() {
        showUser(userService.findAll(), InputOption.FIND);
        System.out.println("░░░░░░ TÌM KIẾM BẰNG TÀI KHOẢN ░░░░░░░");
        System.out.print("Nhập tên tài khoản: ");
        String value = scanner.nextLine();
        List<User> usersFind = userService.findByUserName(value);
        if (usersFind != null) {
            showUser(usersFind, InputOption.FIND);
        } else {
            System.out.println("Không tìm thấy!");
        }
    }

    private void sortById() {
        System.out.println("░░░░░░░░ SẮP XẾP THEO ID ░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortById(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortById(TypeSort.DESC), InputOption.SORT);
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

    private void sortByUserName() {
        System.out.println("░░░░░░░░ SẮP XẾP THEO TÀI KHOẢN ░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByUserName(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByUserName(TypeSort.DESC), InputOption.SORT);
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

    private void sortByFullName() {
        System.out.println("░░░░░░░░ SẮP XẾP THEO TÊN NGƯỜI DÙNG ░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByFullName(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByFullName(TypeSort.DESC), InputOption.SORT);
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

    private void sortByEmail() {
        System.out.println("░░░░░░░░░░ SẮP XẾP THEO EMAIL ░░░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByEmail(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByEmail(TypeSort.DESC), InputOption.SORT);
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

    private void sortByPhone() {
        System.out.println("░░░░░░░░░░ SẮP XẾP THEO SĐT ░░░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByPhone(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByPhone(TypeSort.DESC), InputOption.SORT);
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

    private void sortByRole() {
        System.out.println("░░░░░░░░ SẮP XẾP THEO QUYỀN NGƯỜI DÙNG ░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByRole(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByRole(TypeSort.DESC), InputOption.SORT);
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

    private void sortByAddress() {
        System.out.println("░░░░░░░░░░ SẮP XẾP THEO ĐỊA CHỈ ░░░░░░░░░░");
        AppUtils.menuSort();
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    showUser(userService.sortByAddress(TypeSort.ASC), InputOption.SORT);
                    break;
                case 2:
                    showUser(userService.sortByAddress(TypeSort.DESC), InputOption.SORT);
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

    public void updateFullName(User newUser) {
        String oldName = newUser.getFullName();
        String name = inputFullName(InputOption.UPDATE);
        newUser.setFullName(name);
        userService.update(newUser);
        System.out.printf("Đã thay đổi tên từ '%s' thành '%s'.\n", oldName, name);
        AppUtils.pressAnyKeyToContinue();
    }

    public void updateEmail(User newUser) {
        String oldEmail = newUser.getEmail();
        String email = inputEmail(InputOption.UPDATE);
        newUser.setEmail(email);
        userService.update(newUser);
        System.out.printf("Đã thay đổi email từ '%s' thành '%s'.\n", oldEmail, email);
        AppUtils.pressAnyKeyToContinue();
    }

    public void updatePhone(User newUser) {
        String oldPhone = newUser.getPhone();
        String phone = inputPhone(InputOption.UPDATE);
        newUser.setPhone(phone);
        userService.update(newUser);
        System.out.printf("Đã thay đổi số điện thoại từ %s thành %s\n", oldPhone, phone);
        AppUtils.pressAnyKeyToContinue();
    }

    public void updateAddress(User newUser) {
        String oldAddress = newUser.getAddress();
        String address = inputAddress(InputOption.UPDATE);
        newUser.setAddress(address);
        userService.update(newUser);
        System.out.printf("Đã thay đổi địa chỉ từ %s thành %s\n", oldAddress, address);
        AppUtils.pressAnyKeyToContinue();
    }

    private void updateRole(User newUser) {
        userService.update(newUser);
        System.out.println("Cập nhật quyền thành công!");
        AppUtils.pressAnyKeyToContinue();
    }

    private long inputId(InputOption option) {
        long id;
        switch (option) {
            case UPDATE:
                System.out.println("Nhập Id tài khoản muốn chỉnh sửa: ");
                break;
            case DELETE:
                System.out.println("Nhập Id tài khoản muốn chỉnh xóa: ");
                break;
        }
        boolean isTrue = true;
        do {
            id = AppUtils.retryParseLong();
            boolean isFindId = userService.existById(id);
            if (isFindId) {
                isTrue = false;
            } else {
                System.out.println("Không tìm thấy. Vui lòng nhập lại!");
            }
        } while (isTrue);
        return id;
    }

    private static void setRole(User user) {
        boolean isTrue = true;
        int option;
        menuSetRole();
        do {
            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        user.setRole(Role.ADMIN);
                        isTrue = false;
                        break;
                    case 2:
                        user.setRole(Role.USER);
                        isTrue = false;
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

    private String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập số điện thoại của bạn (Số điện thoại bao gồm 10 chữ số, bắt đầu bằng số 0. VD: 0123456789)");
                break;
            case UPDATE:
                System.out.println("Nhập số điện thoại mới: ");
                break;
        }
        System.out.print(" => ");
        String phone;
        do {
            if (!ValidateUtils.isPhoneValid(phone = scanner.nextLine())) {
                System.out.println("Số điện thoại bao gồm 10 chữ số, bắt đầu bằng số 0 (VD: 0123456789). Vui lòng nhập lại!");
                System.out.print(" => ");
                continue;
            }
            if (userService.existsByPhone(phone)) {
                System.out.println("Số điện thoại " + phone + "đã tồn tại. Vui lòng nhập lại !");
                System.out.print(" => ");
                continue;
            }
            break;
        } while (true);
        return phone;
    }

    private String inputEmail(InputOption option) {
        switch (option) {
            case ADD:
                System.out.println("Nhập email của bạn (VD: lythanhtung@codegym.vn)");
                break;
            case UPDATE:
                System.out.println("Nhập email mới: ");
                break;
        }
        System.out.print(" => ");
        String email;
        do {
            if (!ValidateUtils.isEmailValid(email = scanner.nextLine())) {
                System.out.println("Email không đúng định dạng, vui lòng nhập lại");
                System.out.print(" => ");
                continue;
            }
            if (userService.existsByEmail(email)) {
                System.out.println("Email " + email + " đã tồn tại. Vui lòng nhập lại");
                System.out.print(" => ");
                continue;
            }
            break;
        } while (true);
        return email;
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
                System.out.println("Nhập tên (Ký tự đầu của từng từ phải ghi hoa, VD: Lý Thanh Tùng)");
                break;
            case UPDATE:
                System.out.println("Nhập họ và tên mới: ");
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

    public String inputPassWord() {
        System.out.println("Nhập mật khẩu (mật khẩu phải >= 8 kí tự, VD: Tung@codegym1)");
        System.out.print(" => ");
        String password;
        while (!ValidateUtils.isPassWordValid(password = scanner.nextLine())) {
            System.out.println("Mật khẩu phải >= 8 kí tự trong đó chứa  " +
                    "ít nhất 1 ký tự viết hoa, viết thường, chữ số và kí tự đặt biệt. VD: Tung@123");
            System.out.print(" => ");
        }
        return password;
    }

    private String inputUserName() {
        System.out.println("Nhập tài khoản (6-20 kí tự viết thường không bao gồm dấu cách, có ký tự '-', '_', '.'). VD: tung-codegym");
        System.out.print(" => ");
        String username;
        do {
            if (!ValidateUtils.isUsernameValid(username = AppUtils.retryString())) {
                System.out.println(username + " của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại!");
                System.out.println(" => ");
                continue;
            }
            if (userService.existsByUserName(username)) {
                System.out.println(username + " đã tồn tại. Vui lòng nhập lại!");
                System.out.print(" => ");
                continue;
            }
            break;
        } while (true);
        return username;
    }

    private static void menuSortUser() {
        System.out.println("░░░░░░░░░ SẮP XẾP TÀI KHOẢN ░░░░░░░░░░");
        System.out.println("░                                    ░");
        System.out.println("░    1. Sắp xếp theo Id.             ░");
        System.out.println("░    2. Sắp xếp theo tài khoản.      ░");
        System.out.println("░    3. Sắp xếp theo tên.            ░");
        System.out.println("░    4. Sắp xếp theo email.          ░");
        System.out.println("░    5. Sắp xếp theo số điện thoại.  ░");
        System.out.println("░    6. Sắp xếp theo quyền.          ░");
        System.out.println("░    7. Sắp xếp theo địa chỉ.        ░");
        System.out.println("░    8. Trở lại.                     ░");
        System.out.println("░    0. Thoát.                       ░");
        System.out.println("░                                    ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private static void menuFindUser() {
        System.out.println("░░░░░░░░░ TÌM KIẾM TÀI KHOẢN ░░░░░░░░░");
        System.out.println("░                                    ░");
        System.out.println("░    1. Tìm kiếm theo Id.            ░");
        System.out.println("░    2. Tìm kiếm theo tài khoản.     ░");
        System.out.println("░    3. Tìm kiếm theo tên.           ░");
        System.out.println("░    4. Tìm kiếm theo email.         ░");
        System.out.println("░    5. Tìm kiếm theo số điện thoại. ░");
        System.out.println("░    6. Tìm kiếm theo địa chỉ.       ░");
        System.out.println("░    7. Trở lại.                     ░");
        System.out.println("░    0. Thoát.                       ░");
        System.out.println("░                                    ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    private static void menuUpdateUser() {
        System.out.println("░░░░░░ CHỈNH SỬA TÀI KHOẢN ░░░░░░░");
        System.out.println("░                                ░");
        System.out.println("░    1. Chỉnh sửa tên.           ░");
        System.out.println("░    2. Chỉnh sửa email.         ░");
        System.out.println("░    3. Chỉnh sửa số điện thoại. ░");
        System.out.println("░    4. Chỉnh sửa địa chỉ.       ░");
        System.out.println("░    5. Chỉnh sửa quyền.         ░");
        System.out.println("░    6. Trở lại.                 ░");
        System.out.println("░    0. Thoát.                   ░");
        System.out.println("░                                ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }


    private static void menuSetRole() {
        System.out.println("░░░░░░░░░ CHỌN QUYỀN ░░░░░░░░░");
        System.out.println("░          1. ADMIN          ░");
        System.out.println("░          2. USER           ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    public String findNameById(long id) {
        return userService.findNameById(id);
    }
}