package vn.ltt.shop.utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_!@#&()[{}]:;',?/*~$^+=<>\\.]).{8,20}$";
    public static final String NAME_PATTERN = "^([A-ZÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬĐÈẺẼÉẸÊỀỂỄẾỆÌỈĨÍỊÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢÙỦŨÚỤƯỪỬỮỨỰỲỶỸÝỴ][a-zàảãáạăằẳẵắặâầẩẫấậđèẻẽéẹêềểễếệiìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụỤưừửữứựỳỷỹýỵ]{0,6} ?)*$";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE_PATTERN = "^0[1-9][0-9]{8}$";
    private static final String ADDRESS_PATTERN = "^([A-ZÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬĐÈẺẼÉẸÊỀỂỄẾỆÌỈĨÍỊÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢÙỦŨÚỤƯỪỬỮỨỰỲỶỸÝỴ][a-zàảãáạăằẳẵắặâầẩẫấậđèẻẽéẹêềểễếệiìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụỤưừửữứựỳỷỹýỵ]{0,6} ?)*$";
    private static final String PRODUCT_NAME_PATTERN = "^([A-Z]?[A-Za-z]+ ?[0-9]* ?)+$";
    private static final String MANUFACTURER_PATTERN = "^[A-Z]+$";
    private static final String DATE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])[\\-](0?[1-9]|1[012])[\\-]\\d{4}$";

    private static final String MONTH_PATTERN = "^(0?[1-9]|1[012])[\\-]\\d{4}$";

    private static final String YEAR_PATTERN = "\\d{4}$";

    public static boolean isUsernameValid(String userName) {
        return Pattern.compile(USERNAME_PATTERN).matcher(userName).matches();
    }

    public static boolean isPassWordValid(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public static boolean isFullNameValid(String fullName) {
        return Pattern.compile(NAME_PATTERN).matcher(fullName).matches();
    }

    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
    }

    public static boolean isAddressValid(String address) {
        return Pattern.compile(ADDRESS_PATTERN).matcher(address).matches();
    }

    public static boolean isProductNameValid(String productName) {
        return Pattern.compile(PRODUCT_NAME_PATTERN).matcher(productName).matches();
    }

    public static boolean isManufacturerValid(String manufacturer) {
        return Pattern.compile(MANUFACTURER_PATTERN).matcher(manufacturer).matches();
    }

    public static boolean isDayValid(String day) {
        return Pattern.compile(DATE_PATTERN).matcher(day).matches();
    }

    public static boolean isMonthValid(String month) {
        return Pattern.compile(MONTH_PATTERN).matcher(month).matches();
    }

    public static boolean isYearValid(String year) {
        return Pattern.compile(YEAR_PATTERN).matcher(year).matches();
    }
}
