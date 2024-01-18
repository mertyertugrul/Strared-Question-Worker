package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Question {
    private String id;
    private String note;
    @Setter
    private int timesSolved;

    public Question(String id, String note) {
        this.id = id;
        this.note = note;
        this.timesSolved = 0;
    }

    public Question(String id, String note, int timesSolved) {
        this.id = id;
        this.note = note;
        this.timesSolved = timesSolved;
    }
}
