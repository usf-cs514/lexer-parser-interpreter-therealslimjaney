// The Interpreter “runs” SIMPLE Bytecode programs, modifying memory which is represented with an array of integers.
// Basically I need to get a bytecodeinterpreter object. I should have my parser return one, and call that from this class.
// Alternatively, I can have parser return just the integer arraylist of bytecode, and have this class catch it, and have this
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Represents a Byte Code Interpreter that processes and executes a simple bytecode program.
 */
public class ByteCodeInterpreter {
    private ArrayList<Integer> bytecode; //req
    private ArrayList<Integer> memory; //req
    final int LOAD = 0;
    final int LOADI = 1;
    final int STORE = 2;
    private int accumulator = 0; //private is required here
    private int memorySize; //private is required here

    /**
     * Constructs a ByteCodeInterpreter object with a specified memory size.
     * @param memSize The size of the main memory.
     */
    public ByteCodeInterpreter(int memSize) {
        bytecode = new ArrayList<>(); // holds code for the program
        memorySize = memSize;
        memory = new ArrayList<>(Collections.nCopies(memorySize, 0)); // holds main memory array
    }

    /**
     * Gets the bytecode instructions (getter method required by spec)
     * @return The ArrayList containing bytecode instructions.
     */
    public ArrayList<Integer> getBytecode() {
        return bytecode;
    }

    /**
     * Gets the main memory (getter method required by spec)
     * @return The ArrayList representing main memory
     */
    public ArrayList<Integer> getMemory() {
        return memory;
    }

    /**
     * Generates a string representation of the ByteCodeInterpreter object
     * @return a string containing information about bytecode, memory, accumulator, and other attributes.
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
     * Generates a bytecode instruction with the provided command and operand.
     * @param command The bytecode command (LOAD, LOADI, or STORE).
     * @param operand The operand for the bytecode instruction.
     */
    public void generate(int command, int operand) {
        bytecode.add(command);
        bytecode.add(operand);
    }

    /**
     * Runs the bytecode program, modifying the main memory as needed.
     * It processes each bytecode command and updates the accumulator and memory accordingly.
     */
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

        /**
        * Loads the value at the specified memory address into the accumulator.
        * @param addr the memory address from which to load the value.
        */
        private void load( int addr) {
            accumulator += memory.get(addr);
        }

        /**
        * Loads an immediate value into the accumulator.
        * @param val the immediate value to load into the accumulator.
        */
        private void loadI( int val) {
            accumulator += val;
        }

        /**
        * Stores the current value in the accumulator into the memory at the given address.
        * @param address the memory address where the accumulator value will be stored.
        */
        private void store(int address) {
            //add to appropriate address/index in memory array
                    memory.set(address, accumulator);
                    accumulator = 0;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteCodeInterpreter that = (ByteCodeInterpreter) o;
        return LOAD == that.LOAD && LOADI == that.LOADI && STORE == that.STORE && accumulator == that.accumulator && memorySize == that.memorySize && Objects.equals(bytecode, that.bytecode) && Objects.equals(memory, that.memory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bytecode, memory, LOAD, LOADI, STORE, accumulator, memorySize);
    }

    /**
     * Main method (entry point) for the ByteCodeInterpreter class.
     */
    public static void main() {
        }
    }