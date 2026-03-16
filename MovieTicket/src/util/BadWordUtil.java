package util;

import com.vane.badwordfiltering.BadWordFiltering;

import java.util.Arrays;
import java.util.List;

// util/BadWordUtil.java
public class BadWordUtil {
    BadWordFiltering filtering = new BadWordFiltering();
    private static final BadWordFiltering badWordFiltering = new BadWordFiltering();

    static {
        // 방법 1: String 하나씩
        badWordFiltering.add("바보");

        // 파일로 관리
        // badWordFiltering.readFile("src/main/resources/custom-badwords.txt", ",");
    }

    // 욕설 포함 여부
    public static boolean containsBadWord(String text) {

        return badWordFiltering.blankCheck(text);
    }

    // 욕설 치환
    public static String filter(String text) {
        return badWordFiltering.change(text);
    }

}