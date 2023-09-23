import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser class determines if statements are valid based on the expected structure (syntax) of the language and returns an error if not
 *
 * @author jeswingler
 */

public class Parser {

    // Initialise parsIndex variable that keeps track of the index location of the program in the token list data member
    private int parsIndex = 0;

   // Initialise start and end index variables used to extract and store valid assignment statements from token list
    private int startIndex = 0;
    private int endIndex = 0;
    private int line = 1;

    private IdTable parsIdTable; // need to figure out how to make this private
    private ArrayList<Token> parsTokenList; // Data member for token list as required by spec

    // 2D array list to stores valid statements in the form <ID> = <Expression>
    private ArrayList<ArrayList<Token>> validAssignments;

    /**
     * Creates an instance of the Parser class
     * Enter the fileName argument of lexer here
     */
    public Parser() {
        parsIdTable = new IdTable();
        Lexer parsLexer = new Lexer("testOutOfBounds.txt"); // Lexer is created in Parser constructor as required by project specification
        parsTokenList = parsLexer.getAllTokens();
        validAssignments = new ArrayList<>();
    }

    /**
     * Parses the program by calling parseAssignment on a loop until an error is found or the end of the list of tokens is reached
     */
    private void parseProgram() {
        while (parsIndex < parsTokenList.size()) {
            Token token = parsTokenList.get(parsIndex);
            if (!checkEof(token)) {
                parseAssignment();
            } else {
                parsIndex++; // Increment the index for EOF token to terminate loop
                validProgram();
            }
        }
    }

    /**
     * Parses a single assignment statement of structure: <identifier> <=> <expression>
     * Calls parseID, parseAssignOp and parseExpression
     */
    private void parseAssignment() {
        if (parseId()) {
            // Add the ID token to the idTable
            parsIdTable.add(parsTokenList.get(parsIndex-1)); // Use parsIndex-1 to add the ID (parseID has called nextToken, which increments parsIndex)
            startIndex = parsIndex-1; // Set startIndex of a valid assignment statement
            if (parseAssignOp()) {
                parseExpression();
            } else {
                reportError("Error: Expecting assignment operator, line " + line);
            }
        } else {
            reportError("Error: Expecting identifier, line " + line);
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
     * Parses the expression on the right hand side of an assignment statement
     */
    private void parseExpression() {
        Token token = nextToken();
        // Checks that first term is an id or int
        if (parseIdOrInt(token)) {
            if ("ID".equals(token.type)) {
                // Check if the ID is defined in parsIdTable
                if (parsIdTable.getAddress(token.value) == -1) {
                    reportError("Error: Identifier not defined, line " + line);
                }
            }
        } else {
            reportError("Error: Expecting identifier or integer, line " + line);
        }

        // Determine the next token type
        Token tokenN = parsTokenList.get(parsIndex);

        if (checkEof(tokenN)) { // Check if the end of file has been reached, store assignment
            endIndex = parsIndex;
            storeAssignment(startIndex, endIndex);
        } else if (parseIdOrOp(tokenN)) { //Determine is an ID or assignment operator
            if ("ID".equals(tokenN.type)) { // An ID indicates the start of a new assignment statement, store assignment
                endIndex = parsIndex;
                storeAssignment(startIndex, endIndex);
            } else { // Is an assignment operator
                nextToken(); // Call nextToken to increment parsIndex to account for the assignment operator
                parseExpression(); // Continue parsing expression
            }
        } else { // Neither ID or assignment operator
            reportError("Expecting identifier or add operator, line " + line);
        }
    }

    /**
     * Determines if a token is an ID or INT
     * @param token to be evaluated
     * @return boolean true or false
     */
    private boolean parseIdOrInt(Token token) {
        String tokenType = token.type;
        return ("ID".equals(tokenType) || "INT".equals(tokenType));
    }

    /**
     * Determines if a token is an ID or a '+' Op
     * @param token token to be evaluated
     * @return boolean true or false
     */
    private boolean parseIdOrOp(Token token) {
        String tokenType = token.type;
        return ("ID".equals(tokenType) || "PLUS".equals(tokenType));
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
        line++;
        ArrayList<Token> assignment = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) { // end index is exclusive
            assignment.add(parsTokenList.get(i));
        }
        validAssignments.add(assignment);
    }
    /**
     * Takes in a 2D ArrayList of valid statements and determines commands and operands for each
     * Calls generate method from the ByteCodeInterpreter class to modify bytecode data member of the ByteCodeInterpreter instance
     * @param validAssignments
     */

    // Jane this MUST take in a bytecode object because the generate method called inside this must be called on the bytecode data member of an object
    private void generateByteCode(ArrayList<ArrayList<Token>> validAssignments, ByteCodeInterpreter interp) {
        //Iterate through outer loop of validAssignments
        for (ArrayList<Token> statement : validAssignments) { //Outer loop
            //Iterate through RHS of each valid statement
            for (int i = 2; i < statement.size(); i++) { // From third in statement
                String operand = (statement.get(i)).value;
                if (operand.equals("+")) {
                    continue;
                } else if (operand.matches("\\d+")) { //This will return false if there is anything other than numerics in the string
                    interp.generate(interp.LOADI, Integer.parseInt(operand));
                } else {
                    int operandAddress = parsIdTable.getAddress(operand);
                    interp.generate((interp.LOAD), operandAddress);
                }
            }
            // Evaluate the LHS variable
            interp.generate(interp.STORE, parsIdTable.getAddress(statement.get(0).value));
        }
    }

    private void validProgram() {
        System.out.println("Valid Program");
    }
    /**
     * Calls the parser constructor and parses program
     * @param args
     */
    public static void main(String[]args) {
        Parser parser = new Parser();
        parser.parseProgram();
        ByteCodeInterpreter interpreter = new ByteCodeInterpreter(10);
        parser.generateByteCode(parser.validAssignments, interpreter);
        interpreter.run();
        System.out.println("Symbol Table: " + parser.parsIdTable);
        System.out.println("Bytecode: " + interpreter.getBytecode());
        System.out.println("Memory: " + interpreter.getMemory());
    }
}