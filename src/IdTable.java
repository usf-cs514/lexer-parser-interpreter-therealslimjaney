import java.util.HashMap;

/**
 * The IdTable class tracks the identifiers within a program
 * When an identifier appears as the subject of an assignment operation, it is added to the IdTable
 * When an identifier appears as a term in the assignment expression, the IdTable is checked to see if it is defined
 * @author jeswingler
 */

public class IdTable {

    HashMap<String, Integer> idTable;
    int addressCounter;

    /**
     * Creates a new instance of IdTable class
     *
     */

    public IdTable() {
        idTable = new HashMap<>();
        addressCounter = 0; // Set to -1 so that ...
    }

    /**
     * Adds an entry to the idTable if it is absent
     * @param token id the identifier value is sent
     */
    public void add(Token token) {
        idTable.putIfAbsent(token.value, addressCounter);
        addressCounter++;

    }

    /**
     * Checks the address associated with an id
     * @param value the value if the id
     * @return the address of the id, -1 if ot found
     */
    public int getAddress(String value) {
        int address = idTable.get(value);
        return address;
    }

    public String toString() {
        return idTable.toString();
    }
}
