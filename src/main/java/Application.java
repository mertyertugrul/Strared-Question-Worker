import repository.FileQuestionRepository;
import service.CommandProcessor;
import service.ResourceService;
import util.DatabaseInitializer;


public class Application {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        String path = ResourceService.findResource("src/main/resources/questions")
                .orElseThrow(() -> new RuntimeException("Resource file not found"));
        FileQuestionRepository fileQuestionRepository = FileQuestionRepository.getInstance(path);
        CommandProcessor commandProcessor = new CommandProcessor(fileQuestionRepository);
        commandProcessor.starProcessing();
        System.out.println("Application terminated");
    }


}
