package repository;

import model.Question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class FileQuestionRepository implements QuestionRepository {
    private final List<Question> questions = new ArrayList<>();

    public FileQuestionRepository(String resourceFileName) {
        loadQuestionsFromResource(resourceFileName);
    }

    private void loadQuestionsFromResource(String resourceFileName) {
    try (InputStream inputStream = new FileInputStream(resourceFileName)) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> lines = reader.lines().toList();
        IntStream.range(0, lines.size()/2)
                .mapToObj(i -> parseLineToQuestion(lines.get(i*2), lines.get(i*2 + 1)))
                .forEach(questions::add);
    } catch (Exception e) {
        throw new RuntimeException("Error while reading questions from resource file: " + resourceFileName, e);
    }
}

    private Question parseLineToQuestion(String line1, String line2) {

            String[] lineInfo = line1.split("\\.");
            String id =  new StringBuilder()
                    .append(lineInfo[lineInfo.length - 3])
                    .append(".")
                    .append(lineInfo[lineInfo.length - 2])
                    .append(".")
                    .append(lineInfo[lineInfo.length - 1])
                    .toString();

        return new Question(id.substring(1), line2.subSequence(0, line2.length() - 1).toString());

    }

    @Override
    public Optional<Question> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public Question getRandomQuestion() {
        return null;
    }

    @Override
    public List<Question> getRandomQuestions(int randomQuestionsCount) {
        List<Question> randomQuestions = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<randomQuestionsCount; i++){
            randomQuestions.add(questions.get(random.nextInt(questions.size())));
        }
        return randomQuestions;
    }
}
