/**
 * Represents a lexical token in the SIMPLE programming language
 */
public class Token {
    public String type;
    public String value;

    /**
     * Constructs a new Token with the given type and value.
     * @param type The type of the token.
     * @param value The content of the token.
     */
    public Token(String type, String value) {
        this.type=type;
        this.value=value;
    }

    /**
     * Returns a string representation of the token in the format "type value".
     * @return The string representation of the token.
     */
    public String toString(){

        return type+" "+value;
    }


}
