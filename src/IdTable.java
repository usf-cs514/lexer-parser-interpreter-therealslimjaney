// This class tracks the identifiers within a program
//
//When an identifier appears on the left-hand-side of an assignment statement, add it to the IdTable
//When an identifier appears on the right-hand-side of an assignment statement, check the IdTable to see if the identifier has been defined (it is an error if not).

import java.util.Map;
import java.util.HashMap;

public class IdTable {
    HashMap<String, Integer> idTable;
    int addressCounter;
    //a hashmap data member with String key and Integer value
    /**The keys are the identifiers and the values represent the address in
    memory in which the identifier will be stored (if an interpreter were built)
     You can also just think of it as the order the ids appear- the first id will
     have address 0, the second id will have address 1, and so on.*/
    public IdTable() {
        idTable = new HashMap<>();
        addressCounter=-1;
    }

    public void add(String id) {
        idTable.putIfAbsent(id, addressCounter++);
    }

    public int getAddress(String id) {
        // this method returns the address associated with an id, or -1 if not found.
        int address = idTable.get(id);
        return address;
    }

    @Override
    public String toString() {
        return idTable.toString();
    }
}
    /**
    private String toString() {
        return table;
    }
     */