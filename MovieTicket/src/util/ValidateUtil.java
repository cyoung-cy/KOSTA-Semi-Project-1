package util;

public class ValidateUtil {
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    public static boolean isValidCardInfo(String cardInfo) {
        if(cardInfo.length() != 19
                || cardInfo.charAt(4) != '-'
                || cardInfo.charAt(9) != '-'
                || cardInfo.charAt(14) != '-'){
            return false;
        }

        return true;
    }

    public static boolean isValidPhone(String phone) {
        if (phone.length() != 13
                || phone.charAt(3) !='-'
                || phone.charAt(8) !='-') {
            return false;
        }
        return true;
    }

    public static boolean isValidBirth(String birth) {
        if(birth.length() != 10
                || birth.charAt(4) != '-' || birth.charAt(7) != '-') {
            return false;
        }
        return true;
    }
}
