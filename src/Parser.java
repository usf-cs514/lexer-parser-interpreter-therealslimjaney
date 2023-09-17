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
    HashMap<String, Integer> table = new HashMap<String, Integer>(); //data member for IdTable (required)
    int listIndex=0;
    ArrayList<Token> tList;
    int line = 0; // This is where the error is

    //TODO implement Parser class here
    public Parser(){
        String fileName = "";
        Lexer lexer = new Lexer(fileName);
        tList = lexer.getAllTokens();;
    }


    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        while (listIndex < tList.size()) {
            parseAssignment();
            System.out.println("Valid program");
        }
    }

    private void parseAssignment() {
        // this method should parse a single assignment statement (LHS=RHS)
        // it should call parseId, parseAssignmentOp, and parseExpression.
        if (parseId()) {
            IdTable.add((tList.get(listIndex)).type, table);
            if (parseAssignOp()) {
                parseExpression();
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
        if ("ID".equals(nextToken().type)) {
            return true;
        } else {
            return false;
        }
    }

    //this method parses a single assignment operator
    private boolean parseAssignOp() {
        if ("ASSMT".equals(nextToken().type)) {
                return true;
            } else {
                return false;
        }
    }

    // This method checks if it is an ID or INT. it must be this if it follows a + or follows a =
    private boolean parseIdOrInt(Token t) {
        if ("ID".equals(t.type)) {
            return true;
        } else if ("INT".equals(t.type)) {
            return true;
        }
        return false;
    }

            private void parseExpression() {
                Token t1 = nextToken();
                if (parseIdOrInt(t1)) {
                    if ("ID".equals(t1.type)) {
                        if (IdTable.getAddress(t1.type, table) == -1) {
                            reportError("Identifier not defined");
                        }
                    }
                } else {
                    reportError("Expecting identifier or integer");
                }
                Token t2 = nextToken();
                if ("PLUS".equals(t2.type)) {
                    parseExpression();
                    ;
                } else if ("ID".equals(t2.type)) {
                    listIndex--;
                    parseAssignment();
                }
            }

        //this method gets the next token in the list and increments the index.
        private Token nextToken () {
            listIndex++;
            return tList.get(listIndex);
        }

        // private String toString() {
        // }
        //
        public void reportError (String message){
            System.out.println(message + " on line ");
            System.exit(1);
        }
        public static void main (String[]args){
            Parser parser = new Parser();
            parser.parseProgram();
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
