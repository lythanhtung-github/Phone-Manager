package vn.ltt.shop.view;

import vn.ltt.shop.service.ProductService;

import java.util.Scanner;

public class ProductViewLauncher {
    private static final int SHOW = 1;
    private static final int ADD = 2;
    private static final int UPDATE = 3;
    private static final int DELETE = 4;
    private static final int FIND = 5;
    private static final int SORT = 6;
    private static final int RETURN = 7;
    private static final int EXIT = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final int RETURN_MEMBER = 4;
    private static final int FIND_MEMBER = 2;
    private static final int SORT_MEMBER = 3;
    private static final ProductView productView = new ProductView();

    public static void launch() {
        boolean isTrue = true;
        do {
            menuProductsManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case SHOW:
                        productView.showProduct(ProductService.getInstance().findAll(), InputOption.SHOW);
                        break;
                    case ADD:
                        productView.addProduct();
                        break;
                    case UPDATE:
                        productView.updateProduct();
                        break;
                    case DELETE:
                        productView.deleteProduct();
                        break;
                    case FIND:
                        productView.FindProduct();
                        break;
                    case SORT:
                        productView.sortProduct();
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
                System.out.println(ex.getMessage());
            }
        } while (isTrue);
    }

    private static void menuProductsManager() {
        System.out.println("░░░░░░░░░░░░░░ MENU PRODUCT ░░░░░░░░░░░░░░");
        System.out.println("░                                        ░");
        System.out.println("░        1. Hiện danh sách sản phẩm.     ░");
        System.out.println("░        2. Thêm sản phẩm.               ░");
        System.out.println("░        3. Chỉnh sửa sản phẩm.          ░");
        System.out.println("░        4. Xóa sản phẩm.                ░");
        System.out.println("░        5. Tìm kiếm sản phẩm.           ░");
        System.out.println("░        6. Sắp xếp sản phẩm.            ░");
        System.out.println("░        7. Trở lại.                     ░");
        System.out.println("░        0. Thoát.                       ░");
        System.out.println("░                                        ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }

    public static void memberLaunch() {
        boolean isTrue = true;
        do {
            menuMemberProductsManager();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case SHOW:
                        productView.showProduct(ProductService.getInstance().findAll(), InputOption.SHOW);
                        break;
                    case FIND_MEMBER:
                        productView.FindProduct();
                        break;
                    case SORT_MEMBER:
                        productView.sortProduct();
                        break;
                    case RETURN_MEMBER:
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
                System.out.println(ex.getMessage());
            }
        } while (isTrue);
    }

    private static void menuMemberProductsManager() {
        System.out.println("░░░░░░░░░░░░░░ MENU PRODUCT ░░░░░░░░░░░░░░");
        System.out.println("░                                        ░");
        System.out.println("░        1. Hiện danh sách sản phẩm.     ░");
        System.out.println("░        2. Tìm kiếm sản phẩm.           ░");
        System.out.println("░        3. Sắp xếp sản phẩm.            ░");
        System.out.println("░        4. Trở lại.                     ░");
        System.out.println("░        0. Thoát.                       ░");
        System.out.println("░                                        ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print("=> ");
    }
}