import repository.FileQuestionRepository;
import service.CommandProcessor;
import service.ResourceService;
import util.DatabaseInitializer;


public class Application {
    public static void main(String[] args) {
        String path = ResourceService.findResource("src/main/resources/questions")
                .orElseThrow(() -> new RuntimeException("Resource file not found"));
        DatabaseInitializer.initializeDatabase();
        FileQuestionRepository fileQuestionRepository = new FileQuestionRepository(path);
        CommandProcessor commandProcessor = new CommandProcessor(fileQuestionRepository);
        commandProcessor.starProcessing();
        System.out.println("Application terminated");
    }


}
