package vn.ltt.shop.utils;

import vn.ltt.shop.view.InputOption;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Scanner;

public class AppUtils {
    static Scanner scanner = new Scanner(System.in);

    public static String retryString() {
        String result;
        while (((result = scanner.nextLine()).trim()).isEmpty()) {
            System.out.println("Chuỗi rỗng hoặc chỉ chứa khoảng trống!");
            System.out.print("Vui lòng nhập lại: ");
        }
        return result;
    }

    public static long retryParseLong() {
        long result;
        do {
            System.out.print(" => ");
            try {
                result = Long.parseLong(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Nhập sai! vui lòng nhập lại");
            }
        } while (true);
    }

    public static double retryParseDouble() {
        double result;
        do {
            System.out.print(" => ");
            try {
                result = Double.parseDouble(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Sai cú pháp, vui lòng nhập lại!");
            }
        } while (true);
    }

    public static String doubleToVND(double value) {
        String patternVND = ",### VNĐ";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }

    public static void pressAnyKeyToContinue() {
        System.out.print("Ấn nút bất kỳ để tiếp tục. ");
        scanner.nextLine();
    }

    public static boolean isRetry(InputOption inputOption) {
        do {
            switch (inputOption) {
                case ADD:
                    System.out.println("===> Chọn 'y' để tiếp tục thêm \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case UPDATE:
                    System.out.println("===> Chọn 'y' để tiếp tục sửa \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case DELETE:
                    System.out.println("===> Chọn 'y' để tiếp tục xóa \t|\t 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case SHOW:
                    System.out.println("===> Chọn 'q' để trở lại \t|\t 't' để thoát.");
                    break;
                case FIND:
                    System.out.println("===> Chọn 'y' để tiếp tục tìm kiếm \t|\t 'q' để quay lại\t|\t 't' để thoát.");
                    break;
                case STATISTICAL:
                    System.out.println("===> Chọn 'y' để tiếp tục xem \t|\t 'q' để quay lại\t|\t 't' để thoát.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + inputOption);
            }

            System.out.print(" => ");
            String option = scanner.nextLine();
            switch (option) {
                case "y":
                    return true;
                case "q":
                    return false;
                case "t":
                    System.out.println("Exit the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không đúng. Vui lòng nhập lại!");
                    System.out.print(" => ");
                    break;
            }
        } while (true);
    }

    public static int retryParseInt() {
        int result;
        do {
            System.out.print(" => ");
            try {
                result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Sai cú pháp, vui lòng nhập lại!");
            }
        } while (true);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static void menuDelete() {
        System.out.println("░░░░░ BẠN CÓ MUỐN XÓA KHÔNG? ░░░░░");
        System.out.println("░            1. Có.              ░");
        System.out.println("░            2. Không.           ░");
        System.out.println("░            0. Thoát.           ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Nhập lựa chọn: ");
        System.out.print(" => ");
    }

    public static void menuSort() {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░        1. Tăng dần.         ░");
        System.out.println("░        2. Giảm dần.         ░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("Enter your choice: ");
        System.out.print(" => ");
    }
}