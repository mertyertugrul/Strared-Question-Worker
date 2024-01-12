package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Question {
    private String id;
    private String note;
    private int timesSolved;

    public Question(String id, String note) {
        this.id = id;
        this.note = note;
        this.timesSolved = 0;
    }

}
