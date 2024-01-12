package service;

import model.Question;
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
        if (command.startsWith("get")) {
            handleGetCommand(command);
        } else {
            System.out.println("Unknown command: " + command);
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
        return String.format("ID: %s \nNotes: %s", question.getId(), question.getNote());
    }
}
