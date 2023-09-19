import java.util.ArrayList;

/**
 * Parser class determines if statements are valid based on the expected structure (syntax) of the language and returns an error if not
 *
 * @author jeswingler
 */

public class Parser {
    // I am making a 2D array list to store my complete statements
    // The statements will take the form <ID> = <Expression>
    ArrayList<ArrayList<Token>> validAssignments = new ArrayList<>();
   // Create start and end index variables of valid assignment statements in token list
    int startIndex =0;
    int endIndex =0;

    IdTable parsIdTable; // need to figure out how to make this private
    private ArrayList<Token> parsTokenList; // Data member for token list is required by spec
    private int parsIndex = 0;
    /**
     * Creates an instance of the Parser class
     */
    public Parser() {
        parsIdTable = new IdTable();
        Lexer parsLexer = new Lexer("testMultiplePlus.txt");
        parsTokenList = parsLexer.getAllTokens();
    }

    /**
     * Parses the program by calling parseAssignment on a loop until an error is found or the end of the list of tokens is reached
     */
    private void parseProgram() {
        while (parsIndex < parsTokenList.size()) {
            Token token = parsTokenList.get(parsIndex);
            String tokenType = token.type;
            // Check if the end of file has been reached
            if (!"EOF".equals(tokenType)) {
                parseAssignment();
            } else {
                System.out.println("Valid program.");
                System.exit(1);
            }
        }
    }

    /**
     * Parses a single assignment statement of structure: <identifier> <=> <expression>
     * Calls parseID, parseAssignOp and parseExpression
     */
    private void parseAssignment() {
        if (parseId()) {
            // parseId has called nextToken which incremented the parsIndex, to ensure the correct token is checked and added to idTable, use index (parsIndex-1)
            parsIdTable.add(parsTokenList.get(parsIndex-1));
            startIndex = parsIndex-1;
            if (parseAssignOp()) {
                parseExpression();
            } else {
                reportError("Error: Expecting assignment operator, line 1.");
            }
        } else {
            reportError("Error: Expecting identifier, line 1.");
        }
    }

    /**
     * Parses a single identifier
     * @return boolean true or false
     */
    private boolean parseId() {
        String tokenType = nextToken().type;
        return tokenType.equals("ID");
    }

    /**
     * Parses a single assignment operator ('=')
     * @return boolean true or false
     */
    private boolean parseAssignOp() {
        String tokenType = nextToken().type;
        return tokenType.equals("ASSMT");
    }

    /**
     * Determines if a token is an ID or INT
     * @param token to be evaluated
     * @return boolean true or false
     */
    private boolean parseIdOrInt(Token token) {
        String tokenType = token.type;
        if ("ID".equals(tokenType) || "INT".equals(tokenType)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if a token is an ID or a '+' Op
     * @param token token to be evaluated
     * @return boolean true or false
     */
    private boolean parseIdOrOp(Token token) {
        String tokenType = token.type;
        if ("ID".equals(tokenType) || "PLUS".equals(tokenType)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parses the expression on the right hand side of an assignment statement
     */
    private void parseExpression() {

        // Checks that first term is an id or int
        Token token = nextToken();
        // Checks that first term is an id or int
        if (parseIdOrInt(token)) {
            if ("ID".equals(token.type)) {
                // Check if the ID is defined in parsIdTable
                if (!parsIdTable.idTable.containsKey(token.value)) {
                    reportError("Error: Identifier not defined, line 1.");
                }
            }
        } else {
            reportError("Error: Expecting identifier or integer, line 1.");
        }

        // The expression will continue being parsed if a plus operator follows, otherwise it signals the start of a new assignment statement
        // Check what kind the next token is before calling nextToken and incrementing index
        Token tokenN = parsTokenList.get(parsIndex);

        if (checkEof(tokenN)) {
            endIndex = parsIndex;
            storeAssignment(startIndex, endIndex);
            validProgram();
        }
        if (parseIdOrOp(tokenN)) {
            // If Id, call parse assignment (a new assignment statement)
            if ("ID".equals(tokenN.type)) {
                endIndex = parsIndex;
                storeAssignment(startIndex, endIndex);
                parseAssignment();
            } else {
                nextToken(); // No need to catch this because we know it is an AssignOp
                parseExpression(); // If not an ID, it must be a '+'' (was verified in parseIDOrAssignOp), continue parsing expression
            }
        } else {
            reportError("Expecting identifier or add operator on line 1.");
        }
    }


    /**
     * Gets the next token in the list and increments index
     * @return the next token
     */
    private Token nextToken() {
        Token token = parsTokenList.get(parsIndex);
        parsIndex++;
        return token;
    }

    /**
     * Outputs an error message to the console and terminates program
     *
     * @param message the appropriate error message is pass in as a parameter
     */
    public void reportError(String message){
        System.out.println(message + "\nInvalid Program");
        System.exit(1);
    }

    /**
     * Checks if the end of file token has been reached
     * If the end of file has been reached, the program has thrown no errors, and it is a valid program
     * @param token token to be evaluated
     */
    private boolean checkEof(Token token) {
        return ("EOF".equals(token.type));
    }

    /**
     * This method captures a valid assignment statement and stores it in an ArrayList
     * The assignment is added to the 2D ArrayList of valid assignments
     * @param startIndex
     * @param endIndex
     */
    private void storeAssignment(int startIndex, int endIndex) {
        ArrayList<Token> assignment = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) { // end index is exclusive
            assignment.add(parsTokenList.get(i));
        }
        validAssignments.add(assignment);
    }

    private void validProgram() {
        System.out.println(validAssignments);
        System.out.println("Valid Program");
        System.exit(1);
    }
    /**
     * Calls the parser constructor and parses program
     * @param args
     */
    public static void main(String[]args) {
        Parser parser = new Parser();
        parser.parseProgram();
    }
}