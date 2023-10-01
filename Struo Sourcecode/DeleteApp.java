
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteApp {
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
     * Delete all records from my.db from selected domain
     */
    public void delete(String paths) {
        String sql = "DELETE FROM tFILE";
        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        String SQL = "DELETE FROM tIMP";
        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        String QL = "DELETE FROM TEMP";
        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(QL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        String Q = "DELETE FROM tTEMP";
        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(Q)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        String L = "DELETE FROM tINDEX"; //FIX THE CASCADE 

        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(L)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args, String paths) {
        DeleteApp app = new DeleteApp();
        app.delete(paths);
    }
}