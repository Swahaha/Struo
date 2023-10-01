import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.Locale;

public class RealOverallinital 
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
     * Create a row in tFILE with new file data 
     */
    public void create(String name, String paths) {
        String ql = "INSERT INTO tFILE(FILE_name, FILE_date) VALUES(?, ?)";

        try (Connection conn = connect(paths);
        PreparedStatement pstmt = conn.prepareStatement(ql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    /**
     * Create a row in tTEMP with parsed file contents
     */
    public void insert(String word, int ID, String paths) {
        String tempinsert = "INSERT INTO tTEMP(TEMP_words, TEMP_fileid) VALUES(?, ?)";

        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(tempinsert)) {
            pstmt.setString(1, word);
            pstmt.setInt(2, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

    /**
     * Manage database and eliminate redundancy
     */
    public void manage(String paths) {
        String SQL1 = "DELETE FROM tTEMP WHERE tTEMP.TEMP_words IN (SELECT tREM.REM_words FROM tREM)";
        // Filter tTEMP through tREM
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL1)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL2 = "DELETE FROM tTEMP WHERE TEMP_words = ''";
        // Remove empty word rows
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL2)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL3 = "INSERT INTO tIMP (IMP_words) SELECT DISTINCT TEMP_words  FROM tTEMP  WHERE tTEMP.TEMP_words NOT IN (SELECT tIMP.IMP_words FROM tIMP)";
        // Insert all unique TEMP_words into tIMP that are not there already
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL3)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL4 = "INSERT INTO tINDEX (F_id, I_id) SELECT DISTINCT tTEMP.TEMP_fileid, tIMP.IMP_id FROM tTEMP INNER JOIN tIMP ON tTEMP.TEMP_words = tIMP.IMP_words";
        // Link all IMP_words with their corresponding files
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL4)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL5 = "DELETE FROM tINDEX WHERE tINDEX.F_id NOT IN (SELECT tFILE.FILE_id FROM tFILE)";
        // Remove all tINDEX rows whose File isn't in the folder anymore
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL5)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL6 = "DELETE FROM tIMP WHERE tIMP.IMP_id NOT IN (SELECT tINDEX.I_id FROM tINDEX)";
        // Remove all tIMP rows that don't have a corresponding file anymore
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL6)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}

        String SQL7 = "DELETE FROM tTEMP";
        // Empty tTEMP
        try (Connection conn = connect(paths); PreparedStatement pstmt = conn.prepareStatement(SQL7)) {
            pstmt.executeUpdate(); } catch (SQLException e) {}
    }

    /**
     * Formats date into predictable String
     */

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public static String formatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();

        return localDateTime.format(DATE_FORMATTER);
    }

    /**
     * Determines if files need updating and accordingly updates my.db
     */
    public void main() throws IOException {
        Path currentfile = Path.of("non-volatile.txt");
        String goodpath = Files.readString(currentfile);

        int zz = 0;
        File directoryPath = new File(goodpath);
        String[] contents = new String[directoryPath.listFiles().length];

        for (File file : directoryPath.listFiles()){
            if (FilenameUtils.getExtension(file.getName()).equals("txt")){
                contents[zz] = file.getName();
                zz++;
            }
        }

        String name = null;
        String File_Name;
        String File_Name2;
        int File_id = 0;
        int A = 0;
        int n = 0;
        int m = 0;
        int q = 0;
        int count = 0;
        int deletecount = 0;
        int Delete = 0; 
        String[] deleted = new String[contents.length];
        RealOverallinital app = new RealOverallinital();
        String[] toDelete = new String[100];

        while (contents[count] != null || count != contents.length) {
            name = contents[count];
            File_Name = null;
            String sql = "SELECT * FROM tFILE WHERE File_name = ?";

            Path file = Paths.get(goodpath + "/" + name); 
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            FileTime fileTime = attr.lastModifiedTime();

            n = 0;
            File_id = 0;
            try (Connection conn = connect(goodpath); PreparedStatement pstmt  = conn.prepareStatement(sql)){
                pstmt.setString(1,name);
                ResultSet rs  = pstmt.executeQuery();
                if(rs.next()){
                    File_Name = name;
                }else{
                    app.create(name, goodpath); // name has been created
                    File_id = rs.getInt("FILE_id");
                }
            } catch (SQLException e) {}

            try (Connection conn = connect(goodpath); PreparedStatement pstmt  = conn.prepareStatement(sql)){
                pstmt.setString(1,name);
                ResultSet rs  = pstmt.executeQuery();
                File_id = rs.getInt("File_id");
                if (formatDateTime(fileTime).compareTo(rs.getString("File_date")) != 0){
                    n++; // name has to be updated
                }
            } catch (SQLException e) {}

            if (n==1){
                String SQL = "UPDATE tFILE SET FILE_date = ? WHERE FILE_name = ?";

                try (Connection conn = connect(goodpath); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                    pstmt.setString(1, formatDateTime(fileTime));
                    pstmt.setString(2, name);
                    pstmt.executeUpdate();
                } catch (SQLException e) {}

                File current = new File(goodpath + "/" + name); //Creation of File Descriptor for a file in the correct folder
                // goodpath is the absolute path to the folder domain
                String[] words=null;  //Intialize the word Array
                String remove[] = {".", ";", ":", "\"", ",", " ", "_", "\t", "?", "(", ")"}; // punctuation array to filter our parser through
                FileReader fr = new FileReader(current);  //Creation of File Reader object
                BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                String s; 
                String s1;

                while((s=br.readLine())!=null) {  //Reading Content from the file
                    s1 = s.toLowerCase();    
                    words=s1.split(" ");  //Split the words using spaces
                    for (int a=0; a<words.length; a++) {
                        for (int z=0; z<remove.length; z++){    
                            words[a] = words[a].replace(remove[z],""); // Remove punctuation
                        }
                        if (!words[a].contains(" ")){
                            app.insert(words[a], File_id, goodpath); // Insert data into tTEMP for the correct Database
                        }
                    }
                } // name has been added to tTEMP

                String redundancy = "DELETE FROM tINDEX WHERE tINDEX.F_id = ?";
                try (Connection conn = connect(goodpath); PreparedStatement pstmt = conn.prepareStatement(redundancy)) {
                    pstmt.setInt(1, File_id);
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            count++;
        }

        String SELECT = "SELECT tFILE.FILE_name FROM tFILE";

        try (Connection conn = connect(goodpath); PreparedStatement pstmt  = conn.prepareStatement(SELECT)){
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                for(int z = 0; z < contents.length; z++){
                    if(rs.getString("FILE_name").equals(contents[z])){
                        m = 1;
                        z = contents.length; // File is good
                    }
                }
                if (m == 0){
                    toDelete[q] = rs.getString("FILE_name");
                    q++; // File must be deleted
                }
                m = 0;
            }
        } catch (SQLException e) {}

        for(int z = 0; z < toDelete.length; z++){
            if(toDelete[z] == null) { break; }

            String FD = "DELETE FROM tFILE WHERE FILE_name = ?";

            try (Connection conn = connect(goodpath); PreparedStatement pstmt = conn.prepareStatement(FD)) {
                pstmt.setString(1, toDelete[z]);
                pstmt.executeUpdate();
            } catch (SQLException e) {} // Fiel has been deleted
        }
        app.manage(goodpath);
    }
}

