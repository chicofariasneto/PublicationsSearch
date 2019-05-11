/**
 *
 * @author Francisco Rodrigues
 */
public class Pair {
    private String  before, after,  name;
    
    /**
     * Metodo Construtor.
     * @param before
     * @param after
     * @param name 
     */
    public Pair(String before, String after, String name) {
        this.before = before;
        this.after = after;
        this.name = name;
    }
    
    /**
     * Retorna a String que representa 5(ou menos) palavras
     * antes da palavra Name.
     * @return 
     */
    public String getBefore() {
        return before;
    }
    
    /**
     * Retorna a String que representa 5(ou menos) palavras
     * depois da palavra Name.
     * @return 
     */
    public String getAfter() {
        return after;
    }
    
    /**
     * Retorna a String que representa a palavra do pair
     * @return 
     */
    public String getName() {
        return name;
    }
    
    
}
