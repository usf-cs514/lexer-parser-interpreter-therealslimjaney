import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 */
public class Lexer {

    String buffer;
    int index = 0;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we open
     */
    public Lexer(String fileName) {

        getInput(fileName);
    }

    /**
     * Reads given file into the data member buffer
     * @param fileName name of file to parse
    */
    private void getInput(String fileName)  {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String (allBytes);
        } catch (IOException e) {
            System.out.println ("You did not enter a valid file name in the run arguments.");
            System.out.println ("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer=scanner.nextLine();
        }
    }

    /**
     * Return all the token in the file
     * @return ArrayList of Token
     */
    // So this is my alogirthm, I want it to check if it's a letter, then move to the next, if it's a letter or integer keep moving
    public ArrayList<Token> getAllTokens(){
        //TODO: place your code here for lexing file
        for (int i = 0; i < buffer.length(); i++) {
            char ch = buffer.charAt(i);
            if (Character.isLetter(ch)) {
                int startIndex = i;

                int endIndex;
                // startIndex, endIndex ;
                // stringCopied = buffer.substring(startIndex, endIndex)
                // If it starts with a letter, I must take that letter and all the letters and digits that follow, until I hit an '=', '+', or '$'
                // - its has type ID, then get it's value, and index = i after first token done
            } else if (ch == '=') {
                // If it's an equals, it must be made an assignment 'ASSMTTOKEN'
            } else if (ch == '+') {
                // If it's a plus it's a plus 'PLUSTOKEN'
            } else {
                // Then it's an unknown like '$'
            }
        }
        return new ArrayList<Token>(); // don't forget to change the return statement
    }
/**
 * *ID:* starts with a letter followed by 0 or more letters and digits.
 *
 * *INT*: a sequence of digits
 *
 * *ASSMT*: a single ‘=’
 *
 * *PLUS*: a single ‘+’
 *
 * *UNKNOWN*: e.g., ‘$’
 */



    /**
     * Before your run this starter code
     * Select Run | Edit Configurations from the main menu.
     * In Program arguments add the name of file you want to test (e.g., test.txt)
     * @param args args[0]
     */
    public static void main(String[] args) {
        String fileName="";
        if (args.length==0) {
            System.out.println("You can test a different file by adding as an argument");
            System.out.println("See comment above main");
            System.out.println("For this run, test.txt used");
            fileName="test.txt";
        } else {

            fileName=args[0];
        }
        Lexer lexer = new Lexer(fileName);
        // just print out the text from the file
        System.out.println(lexer.buffer);
        // here is where you'll call getAllTokens

    }
}

/**- Lexer should have a getNextToken method with no params that returns a single token
 - Lexer should have private methods for getIdentifier and getInteger, called by getNextToken,
 that return a token and fill in value.
 */