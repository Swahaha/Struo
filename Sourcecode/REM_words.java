import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sqlitetutorial.net
 */
public class REM_words {
    /**
     * Establish connection with my.db using filepath from non-volatile.txt
     */
    private Connection connect(String paths) {
        String url = "jdbc:sqlite:" + paths + "/my.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Create a row in tREM with user-inputted "stop" words 
     */
    public void insert(String word, String paths) {
        String sql = "INSERT INTO tREM(REM_words) VALUES(?)";

        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    /**
     * Delete a row in tREM with corresponding "stop" word 
     */
    public void Delete(String word, String paths) {
        String Sql = "DELETE FROM tREM WHERE REM_words = ?";

        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(Sql)) {
            pstmt.setString(1, word);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}