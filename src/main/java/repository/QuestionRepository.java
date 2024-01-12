package repository;

import model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Optional<Question> findById(String id);
    List<Question> findAll();
    Question getRandomQuestion();
    List<Question> getRandomQuestions(int n);

}
