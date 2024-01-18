package repository;

import dao.QuestionDao;
import model.Question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class FileQuestionRepository implements QuestionRepository {
    private static final Logger LOGGER = Logger.getLogger(FileQuestionRepository.class.getName());
    private final List<Question> questions = new ArrayList<>();
    private final QuestionDao questionDao = new QuestionDao();
    private final Random random = new Random();
    private final int minTimesSolved = questionDao.getMinTimesSolved();

    private static FileQuestionRepository instance = null;


    private FileQuestionRepository(String resourceFileName) {
        loadQuestionsFromResource(resourceFileName);
    }

    public static FileQuestionRepository getInstance(String resourceFileName){
        if (instance == null){
            instance = new FileQuestionRepository(resourceFileName);
        }
        return instance;
    }

    private void loadQuestionsFromResource(String resourceFileName) {
        try (InputStream inputStream = new FileInputStream(resourceFileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = reader.lines().toList();
            IntStream.range(0, lines.size() / 2)
                    .mapToObj(i -> parseLineToQuestion(lines.get(i * 2), lines.get(i * 2 + 1)))
                    .forEach(question -> {
                        if (!questionDao.questionExists(question.getId())) {
                            questionDao.insertQuestion(question);
                        }
                        questions.add(question);
                    });
        } catch (Exception e) {
            LOGGER.severe("Error while reading questions from resource file: " + resourceFileName);
            throw new RuntimeException("Error while reading questions from resource file: " + resourceFileName, e);
        }
    }

    private Question parseLineToQuestion(String line1, String line2) {
        String id = line1.split(",")[1];
        return new Question(id, line2.subSequence(0, line2.length() - 1).toString());
    }


    private List<Question> getMinTimesSolvedQuestions() {
        List<Question> minTimesSolvedQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getTimesSolved() == minTimesSolved) {
                minTimesSolvedQuestions.add(question);
            }
        }
        return minTimesSolvedQuestions;
    }

    private Question getRandomQuestionFromList(List<Question> questionList) {
        if (!questionList.isEmpty()) {
            return questionList.get(random.nextInt(questionList.size()));
        }
        return null;
    }

    @Override
    public List<Question> getRandomQuestions(int randomQuestionsCount) {
        refreshQuestionsFromDatabase();
        List<Question> minTimesSolvedQuestions = getMinTimesSolvedQuestions();
        List<Question> randomQuestions = new ArrayList<>();
        for (int i = 0; i < randomQuestionsCount; i++) {
            Question randomQuestion = getRandomQuestionFromList(minTimesSolvedQuestions);
            if (randomQuestion != null) {
                randomQuestion.setTimesSolved(randomQuestion.getTimesSolved() + 1);
                questionDao.updateTimesSolved(randomQuestion);
                randomQuestions.add(randomQuestion);
            }
        }
        return randomQuestions;
    }


    public void refreshQuestionsFromDatabase() {
        questions.clear();
        questions.addAll(questionDao.getAllQuestions());
    }

}
