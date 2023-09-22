// The Interpreter “runs” SIMPLE Bytecode programs, modifying memory which is represented with an array of integers.
// Basically I need to get a bytecodeinterpreter object. I should have my parser return one, and call that from this class.
// Alternatively, I can have parser return just the integer arraylist of bytecode, and have this class catch it, and have this
import java.util.ArrayList;


// Basic example:  “x = 5 y = x+3”

public class ByteCodeInterpreter {
    ArrayList<Integer> bytecode; //req
    private ArrayList<Integer> memory; //req
    final int LOAD = 0;
    final int LOADI = 1;
    final int STORE = 2;
    private int accumulator =0; //private is required here
    private int memorySize = 0; //private is required here
    public ByteCodeInterpreter(int memSize) {
        bytecode = new ArrayList<Integer>(); // holds code for the program
        memory = new ArrayList<Integer>(); // holds main memory array
    }

    /**
     * Generate takes arguments (command and operand) and makes byte code?? bytecode being generated
     * @param command
     * @param operand

     * @return ?
     */
    public void generate(int command, int operand) { //Doesnt the type of operand determine the command?
        this.bytecode.add(command);
        this.bytecode.add(operand);
    }
    //this method runs the code in bytecode, modifying memory
    private void run() {
        // will call load, loadi and store methods
    }
    // private methods for each command type, run will call these.
    private void load() {
    }
    private void loadI() {
    }

    private void store() {
    }

    public static void main() {
    }

}