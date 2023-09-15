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
    public static final String INTTOKEN = "INT";
    public static final String IDTOKEN = "ID";
    public static final String ASSMTTOKEN = "ASSMT";
    public static final String PLUSTOKEN = "PLUS";
    public static final String UNKNOWN = "UNKNOWN"; // I added this
    public static final String EOFTOKEN = "EOF"; // I added this
    public static final String MULTIPLY = "MULTIPLY"; // I added this
    public static final String DIVIDE = "DIVIDE"; // I added this
    public static final String SUBTRACT = "SUBTRACT"; // I added this


    /**
     * call getInput to get the file data into our buffer
     *
     * @param fileName the file we open
     */
    public Lexer(String fileName) {
        getInput(fileName);
    }

    /**
     * Reads given file into the data member buffer
     *
     * @param fileName name of file to parse
     */
    private void getInput(String fileName) {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String(allBytes);
        } catch (IOException e) {
            System.out.println("You did not enter a valid file name in the run arguments.");
            System.out.println("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer = scanner.nextLine();
        }
    }

    /**
     * Gets the next token in a file
     * Should have no paramenters
     */
    public Token getNextToken() {
        Token token=null;
        while (index < buffer.length()) {
            char ch = buffer.charAt(index);
            if (Character.isLetter(ch)) {
                token = new Token(IDTOKEN, getIdentifier(index));
            } else if (Character.isDigit(ch)) {
                token = new Token(INTTOKEN, getInteger(index));
            } else if (ch == '+') {
                // add
            } else if (ch == '*') {
                // multiply
            } else if (ch == '-') {
                // subtract
            } else if (ch == '/') {
                // divide
            } else if (ch == ' ') {
                // whitespace
            } else {
                //unknown
            }
        }
        return token;
    }

    // need to get the value of the identifier
    // startIndex is going to be i from getNextToken, that is sent as a parameter to getIdentifier
    private String getIdentifier(int startIndex) {
        String identifier = null;
        while (index < buffer.length()) {
            char ch = buffer.charAt(startIndex + 1);
            if ((Character.isLetter(ch)) || (Character.isDigit(ch))) {
                index++;
                continue;
            } else {
                identifier = buffer.substring(startIndex, index);
                break;
            }
        }
        return identifier;
        }


    private String getInteger(int startIndex) {
        String integer = null;
        while (index < buffer.length()) {

        }
        return integer;
    }

    /**
     * Return all the token in the file
     * @return ArrayList of Token
     */
    public ArrayList<Token> getAllTokens() {
        //TODO: place your code here for lexing file

        ArrayList<Token> tokens = new ArrayList<Token>();

        for (int i = 0; i < buffer.length(); i++) {

            if (i == buffer.length() + 1) {
                Token token = new Token(EOFTOKEN, "-");
                tokens.add(token);
                break;
            }

            char ch1 = buffer.charAt(i);

            if (Character.isLetter(ch1)) {
                int startIndex = i;
                for (int j = startIndex + 1; j < (buffer.length() - (startIndex + 1)); j++) {
                    char ch2 = buffer.charAt(j);
                    if (Character.isLetter(ch2) || Character.isDigit(ch2)) {
                        continue;
                    } else {
                        int endIndex = j - 1;
                        Token token = new Token(IDTOKEN, buffer.substring(startIndex, endIndex));
                        tokens.add(token);
                        break;
                    }
                }

            } else if (Character.isDigit(ch1)) {
                int startIndex = i;
                for (int k = startIndex + 1; k < buffer.length() - (startIndex + 1); k++) {
                    char ch3 = buffer.charAt(k);
                    if (Character.isDigit(ch3)) {
                        continue;
                    } else {
                        int endIndex = k - 1;
                        Token token = new Token(INTTOKEN, buffer.substring(startIndex, endIndex));
                        tokens.add(token);
                    }
                }

            } else if (ch1 == '=') {
                Token token = new Token(ASSMTTOKEN, Character.toString(ch1));
                tokens.add(token);
            } else if (ch1 == '+') {
                Token token = new Token(PLUSTOKEN, Character.toString(ch1));
                tokens.add(token);
            } else {
                Token token = new Token(UNKNOWN, Character.toString(ch1));
            }
        }
        return tokens;
    }



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
        System.out.print(lexer.getAllTokens());
    }
}

/**- Lexer should have a getNextToken method with no params that returns a single token
 - Lexer should have private methods for getIdentifier and getInteger, called by getNextToken,
 that return a token and fill in value.
 */