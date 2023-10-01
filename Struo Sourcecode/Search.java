import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Search
{
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
     * Outputs list of files with specific searched term
     */
    public String[] manage(String word, String paths) {
        int ID = 0;
        int[] IDs = new int[100];
        String[] names = new String[IDs.length];
        int i = 0;

        String SQL1 = "SELECT tIMP.IMP_id FROM tIMP WHERE tIMP.IMP_words = ?";

        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL1)) {
            pstmt.setString(1,word);
            ResultSet rs  = pstmt.executeQuery();
            ID = rs.getInt("IMP_id");
        } catch (SQLException e) {}

        String SQL2 = "SELECT tINDEX.F_id FROM tINDEX WHERE tINDEX.I_id = ?";

        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL2)) {
            pstmt.setInt(1,ID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                IDs[i] = rs.getInt("F_id");
                i++;
            }
        } catch (SQLException e) {}

        i=0;
        while (IDs[i] != 0){
            String SQL3 = "SELECT tFILE.FILE_name FROM tFILE WHERE tFILE.FILE_id = ?";

            try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL3)) {
                pstmt.setInt(1,IDs[i]);
                ResultSet rs  = pstmt.executeQuery();
                names[i] = rs.getString("FILE_name");
                i++;
            } catch (SQLException e) {}
        }
        return(names);
    }

    public static void main(String A) {

    }

}

