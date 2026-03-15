package dto;

import java.util.Arrays;

public enum Genre {
    액션, 애니메이션, 스릴러, 호러, 코미디, 로맨스, 다큐, 드라마, 판타지;

    public static Genre from(String input) {
        return Arrays.stream(values())
                .filter(g -> g.name().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "유효하지 않은 장르입니다: " + input +
                                "\n사용 가능한 장르: " + Arrays.toString(values())
                ));
    }
}