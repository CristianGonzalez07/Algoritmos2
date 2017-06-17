/**
 * Title:        MinMaxAlphaBeta<p>
 * Description:  Engine which implements Minmax Algorithm.<p>         
 * Copyright:    Copyright (c) Gaston Scilingo 2017<p>
 * Company:      Dpto. de Computacion, FCEFQyN, UNRC<p>
 * @author Gaston Scilingo
 * @version 0.1
 */

import java.util.List;


public class MinMaxAlphaBetaEngine<P extends AdversarySearchProblem<S>, S extends AdversarySearchState> extends AdversarySearchEngine<P,S> {

	public MinMaxAlphaBetaEngine(P p,int maxDepth) {
		super(p,maxDepth);
	}
	
	@Override
	public int computeValue(S state) {
		return minmaxAlphaBeta(this.maxDepth,state,problem.minValue(),problem.maxValue());
	}
	
	private int minmaxAlphaBeta(int deep, S state, int alpha, int beta){
		
		if(deep == 1 || problem.end(state))
			return problem.value(state);
		
		boolean max = state.isMax();
		List<S> childs = problem.getSuccessors(state);
		
		for(int i = 0; i<childs.size() && alpha < beta; i++){
			S child = childs.get(i);
			if(max){
				alpha = Math.max(alpha, minmaxAlphaBeta(deep-1,child,alpha,beta));	
			}
			else{
				beta = Math.min(beta, minmaxAlphaBeta(deep-1,child,alpha,beta));
			}
		}
		if(max)
			return alpha;
		return beta;
	}

	@Override
	/*
	 * @see AdversarySearchEngine#computeSuccessor(AdversarySearchState)
	 * @pre. problem!=null and state!=null and !(problem.getSuccessors(state).isEmpty())
	 * @post. the most promising successor for the state is computed, 
	 * via a search in the game tree for state as the root, and 
	 * maxDepth as the maximum depth.
	 * If there is no successor from state an IllegalArgumentException is thrown. 
	*/
	public S computeSuccessor(S state) throws IllegalArgumentException{
		boolean max = state.isMax();
		List<S> successors = problem.getSuccessors(state);
		S best; int bestValue;
		if(successors.size() == 0)
			throw new IllegalArgumentException("Wrong game state input, there is not successors");
		best = successors.remove(0);
		bestValue = computeValue(best);
		for(S child : successors){
			int value = computeValue(child);
			if (max){
				if(value > bestValue){
					best = child; bestValue = value;
				}
			}else{
				if(value < bestValue){
					best = child; bestValue = value;
				}
			}
		}
		return best;
	}

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

}
