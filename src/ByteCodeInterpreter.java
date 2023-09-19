import java.util.ArrayList;


// Basic example:  “x = 5 y = x+3”

public class ByteCodeInterpreter {
    ArrayList<Integer> bytecode;
    ArrayList<Integer> memory;
    final int LOAD = 0;
    final int LOADI = 1;
    final int STORE = 2;
    private int accumulator =0;
    private int memorySize = 0;
    public ByteCodeInterpreter(int memSize) {
        ArrayList<Integer> bytecode = new ArrayList<Integer>(); // holds code for the program
        ArrayList<Integer> memory = new ArrayList<Integer>(); // holds main memory array
    }

    /**
     * Generate takes arguments and adds command to bytecode being generated
     * @param command
     * @param operand
     *
     * @return ?
     */
    public ArrayList<Integer> generate(int command, String operand) {
        return null;
    }

    // private methods for each command type, run will call these.
    private void load() {

    }

    private void loadI() {

    }

    private void store() {

    }

    void run() {

    }
}