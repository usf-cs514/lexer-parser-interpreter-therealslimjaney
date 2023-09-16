import java.util.ArrayList;

// Parser: Determines if a statement or statements is valid based on the expected structure of those statements, and displays an error if not.

/** BNF
 * <assignment-stmt> : <identifier> = <arithmetic-expr>
 *     <arithmetic-expr> : <term> | <arithmetic-expr> + <term>
 *         <term>: <identifier> | <integer>
 */

// Use a recursive descent parsing scheme: define a ParseX method for each particular “part of speech”.
// For example, parseAssignOp will be called when you are expecting a token of type “AssignOp”.
public class Parser {
    ArrayList<Token> tokenList;
    int listIndex=0;

    //TODO implement Parser class here
    public Parser(){
        String fileName = "";
        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = lexer.getAllTokens();
    }

    // Jane! Note, there are assignment statements
    // assignment statement has structure: LHS=RHS
    // Assignment operators:
    // Expression: RHS


    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        while (true) {
            parseAssignment();
        }
    }

    private void parseAssignment() {
        // this method should parse a single assignment statement (LHS=RHS)
        // it should call parseId, parseAssignmentOp, and parseExpression.
    }

    // This method parses a single identifier
    private boolean parseId() {
        if (nextToken().type == "ID") {
            return true;
        } else {
            System.out.println("Expecting identifier");
        }
        return false;
    }

    //this method parses a single assignment operator
    private void parseAssignOp() {
            if (nextToken().type == "ASSMT") {
                return true;
            } else {
                System.out.println("Expecting assignment operator");
            }
            return false;
        }
    }

    private void parseExpression() {
        // this method parses an expression, i.e., the right-hand-side of an assignment
        // Note that expressions can include an unlimited number of “+” signs, e.g., “Y+3+4”
    }

    //this method gets the next token in the list and increments the index.
    private Token nextToken() {
        listIndex++;
        return tokenList.get(listIndex);
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
