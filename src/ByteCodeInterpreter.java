// The Interpreter “runs” SIMPLE Bytecode programs, modifying memory which is represented with an array of integers.
// Basically I need to get a bytecodeinterpreter object. I should have my parser return one, and call that from this class.
// Alternatively, I can have parser return just the integer arraylist of bytecode, and have this class catch it, and have this
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Basic example:  “x = 5 y = x+3”

public class ByteCodeInterpreter {
    private ArrayList<Integer> bytecode; //req
    private ArrayList<Integer> memory; //req
    final int LOAD = 0;
    final int LOADI = 1;
    final int STORE = 2;
    private int accumulator = 0; //private is required here
    private int memorySize; //private is required here

    public ByteCodeInterpreter(int memSize) {
        bytecode = new ArrayList<Integer>(); // holds code for the program
        memorySize = memSize;
        memory = new ArrayList<Integer>(Collections.nCopies(memorySize, 0)); // holds main memory array
    }

    /**
     * Method to getBytecode (req by spec)
     *
     * @return
     */
    public ArrayList<Integer> getBytecode() {
        return bytecode;
    }

    /**
     * Method to get memory (req by spec)
     *
     * @return
     */
    public ArrayList<Integer> getMemory() {
        return memory;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "ByteCodeInterpreter{" +
                "bytecode=" + bytecode +
                ", memory=" + memory +
                ", LOAD=" + LOAD +
                ", LOADI=" + LOADI +
                ", STORE=" + STORE +
                ", accumulator=" + accumulator +
                ", memorySize=" + memorySize +
                '}';
    }

    /**
     * Generate takes arguments (command and operand) and makes byte code?? bytecode being generated
     *
     * @param command
     * @param operand
     * @return ?
     */
    public void generate(int command, int operand) {
        bytecode.add(command);
        bytecode.add(operand);
    }

    //this method runs the code in bytecode, modifying memory
    // will call load, loadI and store methods
    public void run() {
                for (int i = 0; i <= bytecode.size() - 2; i += 2) {
                    switch (bytecode.get(i)) {
                        case 0:
                            int addr1 = bytecode.get(i + 1);
                            load(addr1);
                            break;
                        case 1:
                            int value = bytecode.get(i + 1);
                            loadI(value);
                            break;
                        case 2:
                            int addr2 = bytecode.get(i + 1);
                            if (addr2 < memorySize) {
                                store(addr2);
                            } else {
                                System.out.println("Error: Address out of range.");
                                return;
                            }
                    }
                }
            }

        // private methods for each command type, run will call these.
        private void load( int addr) {
            // load the value at address x to accumulator
            accumulator += memory.get(addr);
        }

        private void loadI( int val) {
            accumulator += val;
        }

        private void store(int address) {
            //add to appropriate address/index in memory array
                    memory.set(address, accumulator);
                    accumulator = 0;
        }

        public static void main() {
        }
    }