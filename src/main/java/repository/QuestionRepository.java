package repository;

import model.Question;
import model.QuestionStar;

import java.util.List;

public interface QuestionRepository {
    List<Question> getRandomQuestions(int n);
    boolean starQuestion(String questionId, QuestionStar star);
    int getUnsolvedQuestionsCount();

    int getSolvedQuestionsCount();
}
