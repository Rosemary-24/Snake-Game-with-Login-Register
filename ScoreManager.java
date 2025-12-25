package login_register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreManager {

    public static void saveScore(String username, int score) {
        int userId = 0;

        // Step 1: Get user ID from users_db
        try (Connection cnxUsers = My_CNX.getConnection("users_db")) {
            if (cnxUsers == null) {
                System.out.println("Failed to connect to users_db.");
                return;
            }

            PreparedStatement pst = cnxUsers.prepareStatement(
                "SELECT id FROM users WHERE username = ?"
            );
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            } else {
                System.out.println("User not found in users_db!");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreManager.class.getName())
                  .log(Level.SEVERE, "Error fetching user ID", ex);
            return;
        }

        // Step 2: Insert score into scores_db
        try (Connection cnxScores = My_CNX.getConnection("scores_db")) {
            if (cnxScores == null) {
                System.out.println("Failed to connect to scores_db.");
                return;
            }

            PreparedStatement pstScore = cnxScores.prepareStatement(
                "INSERT INTO scores(User_ID, Score, Played_at) VALUES(?, ?, NOW())"
            );
            pstScore.setInt(1, userId);
            pstScore.setInt(2, score);
            int rowsAffected = pstScore.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Score saved successfully!");
            } else {
                System.out.println("Failed to save score.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreManager.class.getName())
                  .log(Level.SEVERE, "Error inserting score", ex);
        }
    }
}
