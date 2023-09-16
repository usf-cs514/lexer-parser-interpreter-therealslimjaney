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
    String message = "";

    //TODO implement Parser class here
    public Parser(){
        String fileName = "";
        Lexer lexer = new Lexer(fileName);
        ArrayList<Token> tokenList = lexer.getAllTokens();
    }


    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        while (listIndex < tokenList.size()) {
            parseAssignment();
        }
    }

    private void parseAssignment() {
        // this method should parse a single assignment statement (LHS=RHS)
        // it should call parseId, parseAssignmentOp, and parseExpression.
        if (parseId()) {
            IdTable.add((tokenList.get(listIndex)).type, table);
            if (parseAssignOp()) {
                if (parseExpression()) {
                } else {
                    reportError("Expecting identifier or integer"); // must remove this. Im handling these errors in the method
                }
            } else {
                //error: expecting assignment operator
                reportError("Expecting assignment operator");
            }
        } else {
            reportError("Expecting identifier");
            //error: expecting identifier
        }
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

    // This method checks if it is an ID or INT. it must be this if it follows a + or follows a =
    private boolean parseTerm() {
        Token t = nextToken();
        if (t.type=="EOF" || t.type=="UNKNOWN") {
            reportError("Expecting identifier or integer");
        } else if ((t.type == "ID" && (IdTable.getAddress(t.type, table) != -1) || t.type == "INT") {
            return true;
        }

    }

    private boolean parseExpression() {
        while (true) {
            if (parseTerm()) {
                // if it is an ID or INT, it must be followed by an ID(new statement) or + (continuation of expression)
            } else
            if (t.type == "PLUS") {
                parseTerm();
            } else if (IdTable.getAddress(t.type, table) == -1) {
                listIndex--; //deincrement the listIndex because I want the parseAssignment method to start running from the ID I just found that isnt in the Id table
            }
        }
    }



    //this method gets the next token in the list and increments the index.
    private Token nextToken() {
        listIndex++;
        return tokenList.get(listIndex);
    }

    // private String toString() {
     // }
        //
        public void reportError(String message) {
            System.out.println(message + " on line " + (index + 1));
            System.exit(1);
        }
        public static void main (String[]args) {
        Parser parser = new Parser();
        parser.parseProgram();
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
