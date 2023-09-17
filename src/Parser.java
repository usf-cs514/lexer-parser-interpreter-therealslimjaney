import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
// I need to figure out why parseID is failing. I think I may be using the wrong equals method. I need to do the equals worksheets and the chapter readings.
// Also look on uDemy tomorrow for equals stuff.
// Keep persevering!
// Parser: Determines if a statement or statements is valid based on the expected structure of those statements, and displays an error if not.

/** BNF
 * <assignment-stmt> : <identifier> = <arithmetic-expr>
 *     <arithmetic-expr> : <term> | <arithmetic-expr> + <term>
 *         <term>: <identifier> | <integer>
 */

// Use a recursive descent parsing scheme: define a ParseX method for each particular “part of speech”.
// For example, parseAssignOp will be called when you are expecting a token of type “AssignOp”.
public class Parser {
     //data member for IdTable (required)
    int listIndex=0;
    ArrayList<Token> tList; //Data member for token list is required by spec
    IdTable tableObj; //Also required by spec
    int line = 0; // This is where the error is

    //TODO implement Parser class here
    public Parser(){
        Lexer lexer = new Lexer("test.txt");
        tList = lexer.getAllTokens();
        tableObj = new IdTable();
    }


    private void parseProgram() {
        //this method drives the process and parses an entire program.
        // This method should call parseAssignment within a loop.
        while (listIndex < tList.size()) {
            Token token = tList.get(listIndex);
            String tokenType = token.type;
            if (!"EOF".equals(tokenType)) {
                parseAssignment();
            } else {
                System.out.println("Valid Program");
            }
        }
    }


    private void parseAssignment() {
        // this method should parse a single assignment statement (LHS=RHS)
        // it should call parseId, parseAssignmentOp, and parseExpression.
        int counter = 0;
            if (parseId()) {
                tableObj.add((tList.get(listIndex-1)).type); //This is the problem. the listIndex has been incremented, you are adding the next token, not the ID
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
        String tokenType = nextToken().type;
        return tokenType.equals("ID");
    }

    //this method parses a single assignment operator
    private boolean parseAssignOp() {
        String tokenType = nextToken().type;
        return "ASSMT".equals(tokenType);
    }

    // This method checks if it is an ID or INT. it must be this if it follows a + or follows a =
    private boolean parseIdOrInt(Token t) {
        String tokenType = nextToken().type;
        if ("ID".equals(tokenType) || "INT".equals(tokenType)) {
            return true;
        }
        return false;
    }

    private void parseExpression() {
        Token t1 = nextToken();
        String tokenType = t1.type;
        if (parseIdOrInt(t1)) {
            if ("ID".equals(tokenType)) {
                if (tableObj.getAddress(tokenType) == -1) {
                    reportError("Identifier not defined");
                }
            }
        } else {
            reportError("Expecting identifier or integer");
        }
        Token t2 = nextToken();
        String tokenType2 = t2.type;
        if ("PLUS".equals(tokenType2)) {
            parseExpression();
        } else if ("ID".equals(tokenType2)) {
            listIndex--;
            parseAssignment();
        }
    }

        //this method gets the next token in the list and increments the index.
        private Token nextToken() {
            Token token = tList.get(listIndex);
            listIndex++;
            return token;
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
