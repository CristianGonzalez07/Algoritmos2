/**
 *	This Class defines objects to model a backlog story.
 *	@author Gonzalez,Cristian.
 *	@author Cuello,Santiago.
 *	@version 0.1, 16/06/2017
 */

public class Story {	
  private String description;
  private int priority; 
  private int weight;

  /**
   *	Constructor for class History
   *	@param d is the description associated with the backlog history
   *	@param p is the priority associated with the backlog history
   *	@param w is the weight associated with the backlog history
   *	@pre. true.
   *	@post. this constructor sets description,priority and weight
   * with received params.
   */
  public Story (String d, int p, int w){
    this.description = d;
    this.priority	=	p;
    this.weight	=	w;
  }

  /**
   *	returns the description associated with the backlog history
   * @return A string representing the description of the story
   *	@pre. true.
   *	@post. the value of description is returned
   * with received params.
   */    
  public String getDescription(){
    return this.description;
  }

  /**
   *	returns the priority associated with the backlog history
   * @return A int representing the priority of the story
   *	@pre. true.
   *	@post. the value of priority is returned
   * with received params.
   */  
  public int getPriority(){
    return this.priority;
  }

  /**
   *	returns the weight associated with the backlog history
   * @return A int representing the weight of the story
   *	@pre. true.
   *	@post. the value of weight is returned
   * with received params.
   */  
  public int getWeight(){
    return this.weight;
  }
  
 	/** 
	 * Sets the description associated with the backlog history
	 * @param d is the value used to set this.description
	 * @pre.	d != ""
	 * @post.	this.description is set to d
	*/	 
  public void setDescripcion(String d){
    this.description = d;
  }

  /** 
	 * Sets the priority associated with the backlog history
	 * @param p is the value used to set this.priority
	 * @pre.	p != ""
	 * @post.	this.priority is set to p
	*/
  public void setPriority(int p){
    this.priority = p;
  }

  /** 
	 * Sets the weight associated with the backlog history
	 * @param w is the value used to set this.weight
	 * @pre.	w != ""
	 * @post.	this.weight is set to w
	*/
  public void setWeight(int w){
    this.weight = w;
  }
}