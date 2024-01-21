package model;

import lombok.Getter;

@Getter
public enum QuestionStar {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
    private final int value;

    QuestionStar(int value) {
        this.value = value;
    }

    public static QuestionStar fromInt(int value) {
        return switch (value) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            default -> throw new IllegalArgumentException("Invalid value: " + value);
        };
    }

}
