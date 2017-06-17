
import java.util.List; // necessary due to the use of lists.

/**
 * Interface which defines the basic elements necessary for 
 * characterising a problem as a search. Instances of these 
 * problems should implement this interface, to be able to use 
 * the adversary search strategies.
 * @param S is the class characterising the state for the search
 * problem.        
 * @author Nazareno Aguirre
 * @version 0.1, 17/05/2010
 */
public interface AdversarySearchProblem<S extends AdversarySearchState> {

	/** 
	 * Returns the initial state corresponding to the problem. 
	 * Concrete implementations of AdversarySearchProblem must 
	 * implement this routine, to indicate the starting point for 
	 * the (adversary) search.
	 * @return the initial state for the problem.
	 * @pre. true.
	 * @post. the initial state for the problem is returned.  
	 */
    abstract public S initialState();
    
	/** 
	 * Returns the list of successor states for a given state, in the 
	 * context of the current problem. Concrete implementations of 
	 * AdversarySearchProblem must implement this routine, to indicate
	 * the 'advance' rules (or game rules) for the search.
	 * @param state is the state for which its successors are being 
	 * computed.
	 * @return the list of successor states of state.
	 * @pre. state!=null.
	 * @post. the list of successor states of state is returned.  
	 */
    abstract public List<S> getSuccessors(S state);
        
	/** 
	 * Indicates whether a given state is an end state, i.e., a 
	 * state with no successors. 
	 * @param state is the state being checked to be an end state.
	 * @return true iff state is an end state.
	 * @pre. state!=null.
	 * @post. true is returned iff state is an end state.  
	 */
    abstract public boolean end(S state);

	/** 
	 * Computes the value of a given state. If the state is a leaf
	 * (end state), then this value is an exact value, and indicates
	 * the outcome of the game. If the state is not an end state, then
	 * this value is an approximate value. Its estimation plays a
	 * crucial role in the performance of search. 
	 * @param state is the state for which its value is being computed.
	 * @return an integer value, representing the value of the state.
	 * This value must be greater than minValue(), and smaller than
	 * maxValue().
	 * @pre. state!=null.
	 * @post. an integer value, representing the value of the state.   
	 */
    abstract public int value(S state);
 
    /** 
	 * Indicates the least possible value for a state in the problem.
	 * Together with maxValue(), it determines an interval in which 
	 * values for states must range.
	 * @return an integer value, representing the least possible value
	 * for the problem. 
	 * This value must be strictly smaller than maxValue().
	 * @pre. true.
	 * @post. an integer value, representing the least possible value
	 * for states, is returned. 
	 */
    abstract public int minValue();
    
    /** 
	 * Indicates the greatest possible value for a state in the problem.
	 * Together with minValue(), it determines an interval in which 
	 * values for states must range.
	 * @return an integer value, representing the greatest possible value
	 * for the problem. 
	 * This value must be strictly greater than minValue().
	 * @pre. true.
	 * @post. an integer value, representing the greatest possible value
	 * for states, is returned. 
	 */
    abstract public int maxValue();
        
}

