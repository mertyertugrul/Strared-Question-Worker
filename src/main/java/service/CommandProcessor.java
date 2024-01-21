package service;

import model.Question;
import model.QuestionStar;
import report.Reporter;
import repository.QuestionRepository;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.util.Scanner;


public class CommandProcessor {
    private final QuestionRepository questionRepository;
    private final Scanner scanner;

    public CommandProcessor(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.scanner = new Scanner(System.in);
    }

    public void starProcessing() {
        System.out.println("Enter a command (type exit to stop): ");

        while (true) {
            System.out.println("> ");
            String command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            }

            processCommand(command);
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split("\\s+");
        String commandType = parts[0];

        switch (commandType) {
            case "get" -> handleGetCommand(command);
            case "star" -> {
                if (parts.length > 1)
                    handleStarCommand(command);
                else
                    System.out.println("Unknown command: " + command);
            }
            case "unsolved" -> handleUnsolvedCommand();
            case "solved" -> handleSolvedCommand();
            case "report" -> handleReportCommand();
            default -> System.out.println("Unknown command: " + command);
        }
    }

    private void handleReportCommand() {
        Reporter.createReport().printReport();
    }

    private void handleSolvedCommand() {
        int solvedQuestionsCount = questionRepository.getSolvedQuestionsCount();
        System.out.println("There are " + solvedQuestionsCount + " solved questions");
    }

    private void handleUnsolvedCommand() {
        int unsolvedQuestionsCount = questionRepository.getUnsolvedQuestionsCount();
        System.out.println("There are " + unsolvedQuestionsCount + " unsolved questions");
    }

    private void handleStarCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 3) {
            String questionId = parts[1];
            QuestionStar star = QuestionStar.fromInt(Integer.parseInt(parts[2]));
            boolean success = questionRepository.starQuestion(questionId, star);
            if (success) {
                System.out.println("Question " + questionId + " has been starred with " + star);
            } else {
                System.out.println("Failed to star question " + questionId);
            }
        } else {
            System.out.println("Invalid command. Please use the format: star questionId <id> <star>");
        }
    }

    private void handleGetCommand(String command) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String[] parts = command.split("\\s+");
        int reqNumRanQues = 1;
        if (parts.length > 1) {
            try {
                reqNumRanQues = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + parts[1]);
                return;
            }
        }
        handleQuestions(clipboard, reqNumRanQues);
    }


    private void handleQuestions(Clipboard clipboard, int reqNumRanQues) {
        List<Question> questions = questionRepository.getRandomQuestions(reqNumRanQues);
        questions.forEach(question -> {
            System.out.println(formatQuestion(question));
            Transferable transferable = new StringSelection(question.getId());
            clipboard.setContents(transferable, null);
        });
    }

    private String formatQuestion(Question question) {
        String starInfo = question.getStar() != null ? "\nStar: " + question.getStar() : "";
        return String.format("ID: %s \nNotes: %s%s", question.getId(), question.getNote(), starInfo);
    }
}
