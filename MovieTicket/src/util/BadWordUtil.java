package util;

import com.vane.badwordfiltering.BadWordFiltering;

// util/BadWordUtil.java
public class BadWordUtil {

    private static final BadWordFiltering badWordFiltering = new BadWordFiltering();

    // 욕설 포함 여부
    public static boolean containsBadWord(String text) {
        return badWordFiltering.blankCheck(text);
    }

    // 욕설 치환
    public static String filter(String text) {
        return badWordFiltering.change(text);
    }
}