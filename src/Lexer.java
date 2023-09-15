import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
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
    public static final String MULTOKEN = "MULT"; // I added this
    public static final String DIVTOKEN = "DIV"; // I added this
    public static final String SUBTOKEN = "SUB"; // I added this


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
     * Should have no parameters
     */
    public Token getNextToken() {
        if (index == buffer.length()) {
            return new Token(EOFTOKEN, "-");
        } else {
            while (index < buffer.length()) {
                char ch = buffer.charAt(index);
                if (Character.isLetter(ch)) {
                    index++;
                    return new Token(IDTOKEN, getIdentifier(index));
                } else if (Character.isDigit(ch)) {
                    index++;
                    return new Token(INTTOKEN, getInteger(index));
                } else if (ch == '=') {
                    index++;
                    return new Token(ASSMTTOKEN, "=");
                } else if (ch == '+') {
                    index++;
                    return new Token(PLUSTOKEN, "+");
                } else if (ch == '*') {
                    index++;
                    return new Token(MULTOKEN, "*");
                } else if (ch == '-') {
                    index++;
                    return new Token(SUBTOKEN, "-");
                } else if (ch == '/') {
                    index++;
                    return new Token(DIVTOKEN, "/");
                } else if (ch == ' ') {
                    index++;
                } else {
                    index++;
                    return new Token(UNKNOWN, Character.toString(ch));
                }
            }
        }
        return new Token(EOFTOKEN, "-");
    }


        // need to get the value of the identifier
        // startIndex is going to be i from getNextToken, that is sent as a parameter to getIdentifier
        private String getIdentifier(int startIndex) {
            while (index <= buffer.length()){
                if (index == buffer.length()) {
                    return "-";
                } else {
                    char ch = buffer.charAt(index);
                    if ((Character.isLetter(ch)) || (Character.isDigit(ch))) {
                        index++;
                    } else {
                        return buffer.substring(startIndex - 1, index);

                    }
                }
            }
            return null;
        }


        private String getInteger(int startIndex) {
            while (index <= buffer.length()) {
                if (index == buffer.length()) {
                    return "-";
                } else {
                    char ch = buffer.charAt(index);
                    if (Character.isDigit(ch)) {
                        index++;
                    } else {
                        return buffer.substring(startIndex - 1, index);
                    }
                }
            }
            return null;
        }

        /**
         * Return all the token in the file
         * @return ArrayList of Token
         */
        public ArrayList<Token> getAllTokens() {
            //TODO: place your code here for lexing file
            ArrayList<Token> tokens = new ArrayList<Token>();
            while (true) {
                Token nextToken = getNextToken();
                tokens.add(nextToken);
                if ("EOF".equals(nextToken.type)) {
                    break;
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
        public static void main (String[]args){
            String fileName = "";
            if (args.length == 0) {
                System.out.println("You can test a different file by adding as an argument");
                System.out.println("See comment above main");
                System.out.println("For this run, test.txt used");
                fileName = "test.txt";
            } else {

                fileName = args[0];
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