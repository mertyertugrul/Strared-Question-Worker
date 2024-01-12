package dao;

import model.Question;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuestionDao {
    public void insertQuestion(Question question){
        String sql = "INSERT INTO questions (id, note, timesSolved) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question.getId());
            pstmt.setString(2, question.getNote());
            pstmt.setInt(3, question.getTimesSolved());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
