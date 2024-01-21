package dao;

import model.Question;
import model.QuestionStar;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionDao {
    private static final Logger LOGGER = Logger.getLogger(QuestionDao.class.getName());

    public void insertQuestion(Question question) {
        String sql = "INSERT INTO questions (id, note, timesSolved) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question.getId());
            pstmt.setString(2, question.getNote());
            pstmt.setInt(3, question.getTimesSolved());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Inserting question failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Inserting question failed", e);
        }
    }

    public boolean questionExists(String id) {
        String sql = "SELECT 1 FROM questions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if question exists in database", e);
        }
    }

    public void updateTimesSolved(Question question) {
        String sql = "UPDATE questions SET timesSolved = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, question.getTimesSolved());
            pstmt.setString(2, question.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Updating timesSolved failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Updating timesSolved failed", e);
        }
    }

    public int getMinTimesSolved() {
        String sql = "SELECT MIN(timesSolved) FROM questions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    LOGGER.log(Level.SEVERE, "Unable to retrieve min timesSolved");
                    throw new RuntimeException("Unable to retrieve min timesSolved");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving min timesSolved", e);
        }

    }

    public Collection<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String note = rs.getString("note");
                    int timesSolved = rs.getInt("timesSolved");
                    int star = rs.getInt("star");
                    if(star !=0)
                        questions.add(new Question(id, note, timesSolved, QuestionStar.fromInt(star)));
                    else
                        questions.add(new Question(id, note, timesSolved));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while retrieving all questions", e);
        }
        return questions;
    }

    public boolean updateQuestion(Question question) {
        boolean isUpdated = false;
        String sql = "UPDATE questions SET note = ?, timesSolved = ?, star =? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question.getNote());
            pstmt.setInt(2, question.getTimesSolved());
            pstmt.setInt(3, question.getStar().ordinal() + 1);
            pstmt.setString(4, question.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Updating question failed", e);
        }
        return isUpdated;
    }

    public List<Question> findQuestions(Predicate<Question> p) {
        return getAllQuestions().stream()
                .filter(p)
                .toList();
    }

    public int getStarQuestionsCount(QuestionStar star) {
        return findQuestions(question -> question.getStar() == star).size();
    }

    public int getUnsolvedQuestionsCount() {
        return findQuestions(question -> question.getTimesSolved() == getMinTimesSolved()).size();
    }

    public int getSolvedQuestionsCount() {
        return findQuestions(question -> question.getTimesSolved() > getMinTimesSolved()).size();
    }
}
