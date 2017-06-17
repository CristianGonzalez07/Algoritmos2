import java.util.ArrayList;
import java.lang.Math;

/**
 *	This Class defines objects to model a Sprint.
 *	@author Gonzalez,Cristian.
 *	@author Cuello,Santiago.
 *	@version 0.1, 16/06/2017
 */

public class Sprint {

	private Integer time;				 
	private Integer[][] benefits; 
	private Integer[] weights;
	private Integer[] priorities;
	
 	/**
	 *	Constructor for class Sprint
	 *	@param t is the time limit of the Sprint 
	 *	@param W is the priority of each of sprint activities
	 *	@param w is the weight of each of sprint activities
 	 *	@pre. true.
	 *	@post. this constructor sets time,priorities and weights
	 * with received params.
	 */
	public Sprint(Integer t,Integer[] w,Integer[] p){
		this.time = t;
		this.weights = w;
		this.priorities = p;
		Integer n = w.length;
		Integer i;
		Integer j;
		this.benefits = new Integer[n+1][time+1];
		for(i = 0; i <= time; i++){
			benefits[0][i] = 0; 
		}
		for(i = 0;i <= n;i++){ 
			benefits[i][0] = 0;   
		}
		for(i = 1;i <= n;i++){  
	    	for(j = 1;j <= time;j++){ 
	    		benefits[i][j] = -1;
	    	}
	    } 
	}
  	
  	/**
	  *	Recursive function that returns the value of each benefit associated with a story.
	  *	This function is used to complete the matrix benefits to evaluate the benefits of activities
	  *	according to the sprint time.
	  * @return value of benefits[i][j]
	  *	@param i it's the row number
	  *	@param j it's the column number
	  *	@pre.  i>=0 and j>=0.
	  *	@post. benefits[i][j] is returned as value
  	*/    	 
	public Integer generateSprint(Integer i,Integer j){
		Integer value;
		if (benefits[i][j] < 0){
			if(j<weights[i-1]){
				value = generateSprint(i-1,j);
			}else{
				value = Math.max(generateSprint(i-1,j),priorities[i-1]+generateSprint(i-1,j-weights[i-1]));
			}
			benefits[i][j] = value;
		}
		return benefits[i][j];
	}

	/**
	 *	function that returns a list,listing the stories that allow to maximize the priorities in the established time
	 * @return List of integers where each identifies a history
	 *	@param i it's the row number
	 *	@param j it's the column number
	 *	@pre.  i>=0 and j>=0.
	 *	@post. benefits[i][j] is returned as value
   */  
	public  ArrayList<Integer> getSprint(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		int index = this.weights.length;
		int timeSprint = this.time;

		while(index!=0){
			if (benefits[index][timeSprint] > benefits[index-1][timeSprint]){
				result.add(index); 
				timeSprint = timeSprint - this.weights[index-1];
			}
			index--;
		}
		return result;
	}
}
