import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// Parser: Determines if a statement or statements is valid based on the expected structure of those statements, and displays an error if not.

/** BNF
 * <assignment-stmt> : <identifier> = <arithmetic-expr>
 *     <arithmetic-expr> : <term> | <arithmetic-expr> + <term>
 *         <term>: <identifier> | <integer>
 */

// Use a recursive descent parsing scheme: define a ParseX method for each particular “part of speech”.
// For example, parseAssignOp will be called when you are expecting a token of type “AssignOp”.
public class Parser {
    ArrayList<Token> tokenList; //data member for token list (required)
    HashMap<String, Integer> table = new HashMap<String, Integer>(); //data member for IdTable (required)
    int listIndex=0;
    int line = 0; // This is where the error is

    //TODO implement Parser class here
    public Parser(){
        String fileName = "";
        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = lexer.getAllTokens();
    }


    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        while (true) {
            parseAssignment();
        }
    }

    private boolean parseAssignment() {
        // this method should parse a single assignment statement (LHS=RHS)
        // it should call parseId, parseAssignmentOp, and parseExpression.
        if (parseId()) {
            IdTable.add((tokenList.get(listIndex)).type, table);
            if (parseAssignOp()) {
                if (parseExpression()) {
                    return true;
                } else {
                    System.out.println("Expecting");
                    //error: expecting identifier or integer
                }
            } else {
                //error: expecting assignment operator
                System.out.println("Expecting assignment operator on line: " + line);
            }
        } else {
            System.out.println("Expecting identifier on line: " + line);
            //error: expecting identifier
        }
        return false;
    }

    // This method parses a single identifier
    private boolean parseId() {
        if (nextToken().type == "ID") {
            return true;
        } else {
            return false;
        }
    }

    //this method parses a single assignment operator
    private boolean parseAssignOp() {
        if (nextToken().type == "ASSMT") {
            return true;
        } else {
            return false;
        }
    }

    //An undefined identifier can't be in an expression, so when I reach one, I have finished parsing the expression
    //Now it is up to parseID to either store the new ID in table. If an assignment operator doesnt follow it, we in trouble?
    private boolean parseExpression() {
        Token tokenToScrutinize = nextToken();
        if (tokenToScrutinize.type == "INT") {
            parseExpression();
        } else if (tokenToScrutinize.type == "PLUS") {
            parseExpression();
        } else if (IdTable.getAddress(tokenToScrutinize.type, table) != -1) {  // is this method gotta be made public?
            parseExpression();
        } else {
            return false;
        }
        return false;
    }

        // this method parses an (arithmetic)expression, i.e., the right-hand-side of an assignment
        // Note that (arithmetic)expressions can include an unlimited number of “+” signs, e.g., “Y+3+4”
        // Syntax for <arithmetic-expr>: <term> | <arithmetic-expr> + <term>
        // Syntax for term: <term>: <identifier> | <integer>
        // JANE: So basically when we check if it is a valid expression, we need to do the following:
        //  - look for term (identifier or integer or PLUS+)
        //      - if PLUS+, move to next token (we are still in the arithmetic expression)
        //      - if integer, move to next token (we are still in the arithmetic expression)
        //      -if identifier, check the ID table to see if it is defined.
        //          -if defined, move to next token (we are still in the arithmetic expression)
        //          if not defined,
        //              -we must have reached a new assignment statement
        // Now note, we can't declare a new ID and then set it equal to an undefined ID. That must throw an error somewhere.

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
