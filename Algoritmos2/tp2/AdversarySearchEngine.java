
public abstract class AdversarySearchEngine<P extends AdversarySearchProblem<State>, State extends AdversarySearchState> {

    
    protected P problem; // a reference to the problem to apply search to
    
    protected int maxDepth; // indicates the maximum depth used for the search
    
	/** 
	 * Constructor for abstract class AdversarySearchEngine 
	 * @pre. true.
	 * @post. This constructor sets maxDepth to 1.
	 */
	public AdversarySearchEngine() {
		maxDepth = 1;
	};
    
	/** 
	 * Constructor for abstract class AbstractSearchEngine.
	 * @param p is the abstract search problem associated with the engine
	 * being created.
	 * @pre. p!=null.
	 * @post. A reference to p is stored in field problem and maxDepth
	 * is set to 1.
	 */	
    public AdversarySearchEngine(P p) {
        problem = p;
        maxDepth = 1;
    }
    
	/** 
	 * Constructor for abstract class AbstractSearchEngine.
	 * @param p is the abstract search problem associated with the engine
	 * being created.
	 * @param maxDepth is a value to set this.maxDepth to.
	 * @pre. p!=null and maxDepth>=1.
	 * @post. A reference to p is stored in field problem and 
	 * this.maxDepth is set to maxDepth.
	 */	
    public AdversarySearchEngine(P p, int maxDepth) {
        problem = p;
        this.maxDepth = maxDepth;
    }
    
	/** 
	 * Returns the maximum depth to be used for search. This value
	 * indicates how many game moves in the future are going to be
	 * explored in order to compute the value for a state.
	 * @return the maximum depth to be used for search.
	 * @pre. true.
	 * @post. the value of maxDepth is returned.
	 */	   
	public int getMaxDepth() {
		return maxDepth;
	}

	/** 
	 * Sets the maximum depth to be used for search. This value
	 * indicates how many game moves in the future are going to be
	 * explored in order to compute the value for a state.
	 * @param maxDepth is the value used to set this.maxDepth. 
	 * @pre. maxDepth >= 1.
	 * @post. this.maxDepth is set to maxDepth.
	 */	 
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	/** 
	 * Returns the problem associated with this search engine.
	 * @return a reference to the problem associated with this
	 * search engine. 
	 * @pre. true.
	 * @post. this.problem is returned.
	 */	 
	public P getProblem() {
		return problem;
	}

	/** 
	 * Sets the problem associated with the search engine.
	 * @param p is the search problem to be used for search (to set 'problem' to).
	 * @pre. p!=null.
	 * @post. 'problem' is set to p.
	 */	
    public void setProblem(P p) {
        problem = p;
    }
    
    /** 
	 * Starts the search in order to compute a value for a state. The
	 * computation is performed by exploring the game tree corresponding
	 * to the problem being analysed, considering state as the root,
	 * and with maximum depth maxDepth.
	 * @param state is the state for which its value is being computed.
	 * @return the value computed for the state.
	 * @pre. problem!=null and state!=null.
	 * @post. the value for the state is computed, via a search in the
	 * game tree for state as the root, and maxDepth as the maximum 
	 * depth. 
	 */
    abstract public int computeValue(State state);
    
    /** 
	 * Starts the search in order to compute a most promising successor
	 * for a state. The computation is performed by exploring the game 
	 * tree corresponding to the problem being analysed, considering 
	 * state as the root, and with maximum depth maxDepth.
	 * @param state is the state for which its most promising successor
	 * is being computed.
	 * @return the most promising successor for state. The method
	 * ruleApplied() in the result indicates which rule led to the 
	 * state. 
	 * @pre. problem!=null and state!=null.
	 * @post. the most promising successor for the state is computed, 
	 * via a search in the game tree for state as the root, and 
	 * maxDepth as the maximum depth. 
	 */
    abstract public State computeSuccessor(State state);
    
    
	/** 
	 * Reports information regarding a previously executed search.   
	 * @pre. computeSuccessor(s) or computeValue(s) have been 
	 * executed and finished.
	 * @post. A report regarding the last performed search is printed 
	 * to standard output.
	 */
    abstract public void report();
	
}

