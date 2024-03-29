import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class to build an array of tokens from an input file
 *
 * @see Token
 * @see Parser
 */
public class Lexer {

    private String buffer;

    private int lexerIndex = 0;

    public static final String INTTOKEN = "INT";
    public static final String IDTOKEN = "ID";
    public static final String ASSMTTOKEN = "ASSMT";
    public static final String PLUSTOKEN = "PLUS";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String EOFTOKEN = "EOF";
    public static final String MULTOKEN = "MULT";
    public static final String DIVTOKEN = "DIV";
    public static final String SUBTOKEN = "SUB";


    /**
     * Creates a new instance of Lexer class
     * @param fileName the file taken in as input
     */
    public Lexer(String fileName) {

        getInput(fileName); // call getInput to get the file data into our buffer
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
     * Gets the next token from buffer
     *
     * @return returns the next token object in the buffer
     */
    public Token getNextToken() {
        // Check if we have reached the end of file to avoid out of bounds error
        if (lexerIndex == buffer.length()) {
            return new Token(EOFTOKEN, "-");
        } else {
            while (lexerIndex < buffer.length()) {
                char ch = buffer.charAt(lexerIndex);
                if (Character.isLetter(ch)) {
                    lexerIndex++;
                    return new Token(IDTOKEN, getIdentifier(lexerIndex-1));
                } else if (Character.isDigit(ch)) {
                    lexerIndex++;
                    return new Token(INTTOKEN, getInteger(lexerIndex-1));
                } else if (ch == '=') {
                    lexerIndex++;
                    return new Token(ASSMTTOKEN, "=");
                } else if (ch == '+') {
                    lexerIndex++;
                    return new Token(PLUSTOKEN, "+");
                } else if (ch == '*') {
                    lexerIndex++;
                    return new Token(MULTOKEN, "*");
                } else if (ch == '-') {
                    lexerIndex++;
                    return new Token(SUBTOKEN, "-");
                } else if (ch == '/') {
                    lexerIndex++;
                    return new Token(DIVTOKEN, "/");
                } else if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') { //Skip whitespace, new lines and tabs
                    lexerIndex++;
                } else {
                    lexerIndex++;
                    return new Token(UNKNOWN, Character.toString(ch));
                }
            }
        }
        return new Token(EOFTOKEN, "-");
    }

    /**
     * Retrieves an identifier from the buffer starting at the given startIndex
     * An identifier consists of letters and digits and is terminated by a character that is not a letter or digit
     *
     * @param startIndex the starting lexerIndex from which to begin searching for the identifier in buffer (lexerIndex-1 of buffer is sent in)
     * @return the identifier found in the buffer
     */
    private String getIdentifier(int startIndex) { // lexerIndex-1 is passed in as startIndex parameter so startIndex is preserved to create substring after incrementing lexerIndex in method
        // Iterate through the buffer
        while (lexerIndex <= buffer.length()) {
            // If we reach the end of the buffer return the substring from startIndex to the end
            if (lexerIndex == buffer.length()) {
                return buffer.substring(startIndex, lexerIndex);
            } else {
                char ch = buffer.charAt(lexerIndex);
                // Check if the character is a letter or digit
                if ((Character.isLetter(ch)) || (Character.isDigit(ch))) {
                    lexerIndex++; // If it is increment lexerIndex, while startIndex is preserved
                } else {
                    return buffer.substring(startIndex, lexerIndex); // If not, return the substring as identifier

                }
            }
        }
        return null; // Should not be reached
    }

    /**
     * Retrieves an integer from the buffer starting at the given startIndex
     * An integer is defined as a sequence of digits (0-9)
     *
     * @param startIndex the starting lexerIndex from which to begin searching for the integer (lexerIndex-1 of buffer is sent in)
     * @return the integer found in the buffer, or null if no valid integer is found (null should never be returned)
     */

    private String getInteger(int startIndex) { // lexerIndex-1 is passed in as startIndex parameter so startIndex is preserved to create substring after incrementing lexerIndex
        // Iterate through the buffer
        while (lexerIndex <= buffer.length()) {
            // If we reached the end of the buffer, return the substring from startIndex to the end
            if (lexerIndex == buffer.length()) {
                return buffer.substring(startIndex, lexerIndex);
            } else {
                char ch = buffer.charAt(lexerIndex);
                // Check if the character is a digit (0-9)
                if (Character.isDigit(ch)) {
                    lexerIndex++; // If it is increment lexerIndex, while startIndex is preserved
                } else {
                    return buffer.substring(startIndex, lexerIndex); // If not, return the substring as integer
                }
            }
        }
        return null; // Should not be reached
    }

    /**
     * Returns all the tokens in the file
     *
     * @return ArrayList of Tokens
     */
    public ArrayList<Token> getAllTokens() {
        // Create an ArrayList object to store tokens
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Retrieve Tokens until we reach the end of a file
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
     * Returns a string representation of the Lexer object, including its buffer content.
     * @return A string
     */
    @Override
    public String toString() {
        return "Lexer{" +
                "buffer='" + buffer + '\'' +
                '}';
    }

    /**
     *
     * Compares this Lexer object to the specified object
     * @param o The object to compare this Lexer against.
     * @return True if the given object is equal to this Lexer; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexer lexer = (Lexer) o;
        return lexerIndex == lexer.lexerIndex && Objects.equals(buffer, lexer.buffer);
    }

    /**
     * The entry point for the lexer class
     * If a valid file name is not given, the user is prompted to enter a string
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

            fileName = args[0]; // Use configuration file
        }
    }

}