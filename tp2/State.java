import java.util.ArrayList;
import java.util.Arrays;

/*
    CONSIDERACIONES
 * Hay muchos comentarios que sacar, to te olvides de cambiar 
  *de tipo abstract a public
  * Trata de no pasar las 80 filas de codigo asi queda bien ordenado
  * 
  *Para clonar una matriz en la api de Arrays hay un ".clone"
*/



public class State implements AdversarySearchState {
    private ArrayList<Token> tokens;
    private boolean max;
    private char [][] board;

    /** 
     * Indicates whether the current state is a max state or not.
     * If the current state is not a 'max' state, then it is assumed
     * to be a min state. 
     * @return true iff 'this' is a max state.
     * @pre. true.
     * @post. true is returned iff 'this' is a max state.
     */

    /*
     * La letra 'P' que acompaña a los parametros es para distinguirlos.   
    */

    //Debatir si es necesario inicializar como parametro el board o crearlo dentro de
    // esta clase a partir de los tokens
    public State(ArrayList<Token> tokensP, boolean maxP,int [][] boardP){
        this.tokens =tokensP;
        this.max = maxP;
        this.board = boardP;
    }

    public boolean isMax(){
        return max;
    }

    /** 
     * Checks whether 'this' is equal to another state. 
     * @param other is the state to compare 'this' to.
     * @return true iff 'this' is equal, as a state, to 'other'.
     * @pre. other!=null.
     * @post. true is returned iff 'this' is equal, as a state, 
     * to 'other'.
     */
     public boolean equals(State other){
        return ((max == max)&&((this.tokens).equals(other)));
    }

    /** 
     * Returns a representation as a string of the current state. 
     * @return a string representing the current state.
     * @pre. true.
     * @post. A text representation of the current state is returned.
     */
     /*
    abstract public String toString();
    */

    /** 
     * Returns an object representing the rule applied, leading to the
     * current state. 
     * @return an object representing the rule applied, leading to the
     * current state. If the state is the initial state, then null is 
     * returned.
     * @pre. true.
     * @post. An object representing the rule applied, leading to the
     * current state, is returned. If the state is the initial state, 
     * then null is returned.
     * TODO Replace Object by a more specific class or interface.
     */
     /*
    abstract Object ruleApplied();
    */
}