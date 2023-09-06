import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.*;

class Context
{
    String[] output = new String[10];
    /**
     * Outputs array of relevant context snippets given file name and search term
     */
    public void contextsearch(String input, String file, String paths) throws Exception
    {
        File current = new File(paths + "/" + file);
        FileReader fr = new FileReader(current); 
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s; 
        String s1;
        int count = 0;
        String line; 
        while ((s=br.readLine())!=null){ //Parses through text file line-by-line
            s1=s.toLowerCase(); // Sets input-stream to lower case for consistency
            int window1 = 40; // 80 is an arbitrary index window size for the context
            int window2 = 40;
            // These windows dynamically set the size of the context string
            if(s1.matches(".*\\b" + input + "\\b.*")){ // Uses regex to determine boundaries of a word
                int wordindex = s1.indexOf(input); 
                int lastindex = s1.length();
                if(wordindex < window1){
                    window1 = wordindex; // Ensure window doesn't start below index 0
                }
                if(lastindex - wordindex < window2){
                    window2 = lastindex - wordindex; // Ensure window doesn't end after final String index
                }
                if (wordindex > -1){
                    line = s;
                    line = line.substring(wordindex - window1, wordindex + window2); // get substring within window
                    output[count] = ("... " + line + " ...");
                    count++; // To store the possible multiple usages of a search term
                }
            }
        }
    }
}

