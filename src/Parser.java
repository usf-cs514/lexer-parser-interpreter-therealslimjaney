import java.util.ArrayList;
import java.util.HashMap;
public class Parser {
    ArrayList<Token> tokenList;;

    //TODO implement Parser class here
    public Parser(){
        String fileName = "";
        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = lexer.getAllTokens();
    }

    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        parseAssignment();
    }

    private void parseAssignment() {
        // this method should parse a single assignment statement
        // it should call parseId, parseAssignmentOp, and parseExpression.
    }

    private void parseId() {
        // this method parses a single identifier
    }

    private void parseAssignOp() {
        //this method parses a single assignment operator
    }

    private void parseExpression() {
        // this method parses an expression, i.e., the right-hand-side of an assignment
        // Note that expressions can include an unlimited number of “+” signs, e.g., “Y+3+4”
    }

    private void nextToken() {
        //this method gets the next token in the list and increments the index.
    }

    private String toString() {

    }
    public static void main (String[]args) {
        // Create a lexer and call getAllTokens, then loop through those tokens
        // Define a parseX method for each "part of speech"
            // Eg, a parseAssignOp will be called when you are expecting a token type of AssignOp
    }
}

/**
 * The Parser should find and report the following errors:
 *
 * - Expecting identifier
 * - Expecting assignment operator
 * - Expecting identifier or integer
 * - Expecting identifier or add operator
 * - Identifier not defined
*/
