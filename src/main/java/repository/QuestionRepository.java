package repository;

import model.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> getRandomQuestions(int n);



}
