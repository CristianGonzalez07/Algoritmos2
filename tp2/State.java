import java.util.ArrayList;

public class State implements AdversarySearchState {

    private char[][] board;
    private int playerTokens;
    private int cpuTokens;
    private boolean max;
    private String ruleApplied;
    private int block;

    /**
     * Constructor  
     * @param max indicates whether the state to be created is maximum or minimum.
     * @pre. true.
     * @post. Creates an object of type 'state'
     */
    public State(boolean max){
        this.playerTokens=14;
        this.cpuTokens=14;
        this.max=max;
        this.ruleApplied=null;
        this.block = 0;
        this.board=new char[7][7];
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                board[i][j]='_';            
            }       
        }
    } 

    /**  
     * @return true iff the corresponding position is available to insert.
     * @param i Indicates the column corresponding to the token to enter
     * @param j Indicates the row corresponding to the token to enter
     * @pre. i>=0 i<=6 j>=0 j<=6.
     * @post. Insert the token on the board in the entered position and
     * @post. Insert the token on the board in the entered position,
     * decreases the remaining tokens of the corresponding player and
     * return true iff the corresponding position is available.  
     */
    public Boolean addToBoard(Token token){
        boolean res = false;
        int i = token.getRow();
        int j = token.getColumn();
        char c = token.getColor();
       	
		if(!ocuppied(token)){
	        if(c == 'n'){
	           	this.cpuTokens--;
	           	this.board[i][j]='n';
	           	this.ruleApplied = "añadir ficha "+c+" en : ("+i+","+j+")";  
           	}
	       	if(c=='b'){
	           	this.playerTokens--;
	         	this.board[i][j]='b';
	           	this.ruleApplied = "añadir ficha blanca en : ("+i+","+j+")";
	        }
	        return true;

        }else{
        	return false;
        }
    }
    
    public void block(char c){
    	
    	if(c == 'b'){
    		this.block=3;
    	}else{
    		this.block=-3;
    	}
    	
    		
    }

    /**  
     * @param i Indicates the column corresponding to the token to enter.
     * @param j Indicates the row corresponding to the token to enter.
     * @param c Indicates the color of the token to remove. 
     * @pre. i>=0  && i<=6 && j>=0 && j<=6 && (c== 'n' || c == 'b').
     * @post. Remove the token on the board in the entered position and 
     * Increases the amount of tokens remaining for playing to whoever
     * corresponds        
     */
    public void removeFromBoard(Token token){
        int i = token.getRow();
        int j = token.getColumn();
        char c = token.getColor();

        if(c == 'b'){
            // '?' represents white token removed from the board 
            this.board[i][j]='?';
            this.playerTokens++;
        }else{
            // '¿' represents black token removed from the board
            this.board[i][j]='¿';
            this.cpuTokens++;   
        }
        this.ruleApplied = "remover ficha "+c+" en :("+i+","+j+")";
        
    }

    /**  
     * @return true iff 'this' is a max state.
     * @pre. true.
     * @post. true is returned iff 'this' is a max state.
     */
    public boolean isMax(){
        return this.max;
    }


    public int getPlayerTokens(){
        return this.playerTokens;
    }

    public int getCpuTokens(){
        return this.cpuTokens;
    }


    /**  
     * @pre. true.
     * @post. Exchange the current value of max with its opposite.
     */
    public void switchState(){
    	System.out.println("block es : "+block);
    	if((block == -2)||(block == 2)){
    		enablePositions();
    		block = 0;
    	}
    	
        if(this.max){
            this.max = false;
            block--;
        }else{
            this.max = true;
            block++; 
        }
        System.out.println("block paso a : "+block);
    }


    /**  
     * @param other 'other' is the state to compare with 'this'.
     * @return true iff 'this' is equal to 'other'.
     * @pre. true.
     * @post. true is returned iff 'this' is equal to 'other'.
     */
    public boolean equals(State other){
        boolean res = true;
        
        if(this.max != other.max){
            res=false;
        }

        if(this.playerTokens != other.playerTokens){
            res=false;
        }

        if(this.cpuTokens != other.cpuTokens){
            res=false;
        }

        if(this.ruleApplied != other.ruleApplied){
            res=false;
        }

        boolean equal = true;
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                equal = equal && this.board[i][j]==other.board[i][j];
            }
        }
        return res;
    }

    /**
     * @return A string representing this state.   
     * @pre. true.
     * @post. return a string representing this state. 
     */
    public String toString(){
        String max = "Es maximo: "+this.max+" | ";
        String fichas= "Cant. fichas del jugador : "+this.playerTokens +" | Cant. fichas del Cpu : "+this.cpuTokens+"\n";
        Token token = new Token();
        String acum="";        
        for(int i=0;i<7;i++) {
            for(int j=0;j<7;j++) {
                    token.setRow(i);
                    token.setColumn(j);
                    token.setColor(this.board[i][j]);
                    if(ocuppied(token)){
                        acum+=","+token.toString(); 
                    }
                            
            }   
        }
        String listaT="Las fichas en el tablero son: \n"+acum;
        return (max+fichas+listaT);
    }

    /**
     * @return A string representing the last rule applied.   
     * @pre. true.
     * @post. return a string representing the last rule applied. 
     */
    public Object ruleApplied(){
        return this.ruleApplied;
    }
    
    /**
     * @param t is the token that you want to validate your insert.
     * @return true iff The position where will be insert the token
     * is occupied.   
     * @pre. true.
     * @post. return true iff The position where will be insert the
     * token is occupied. 
     */
    public boolean ocuppied(Token t){
        int row = t.getRow();
        int column = t.getColumn();
        return this.board[row][column] != '_';
    }

    /**
     * @pre. true.
     * @post. enable the disabled positions 
     */
    public void enablePositions(){
    //nota: cuando elija sucesores o juegue el player primero hacer switchState y despues 
    // bloquear los lugares q correspondan.
    for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                if((this.board[i][j]=='?')||(this.board[i][j]=='¿')){
                    this.board[i][j]='_';
                }
            }
        }
    }
    
    /**
     * @return a copy of the board .  
     * @pre. true.
     * @post. returns a copy of state 'this'.
    */
    public State cloneState(){
        State clone = new State(this.max);
        clone.playerTokens = this.playerTokens;
        clone.cpuTokens = this.cpuTokens;
        clone.max = this.max;
        clone.ruleApplied = this.ruleApplied;
        clone.board = cloneBoard();
        clone.block = this.block;
        return clone;
    }

    /**
     * @return a copy of the board belonging to the current state.  
     * @pre. true.
     * @post. returns a copy of the board belonging to the current state.S 
    */
    public char[][] cloneBoard(){
        char[][] clone = new char[7][7];
         for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                clone[i][j]=this.board[i][j];
            }
        }
        return clone;
    }
  
    public boolean willRemoved(Token token){
        int row = token.getRow();
        int column = token.getColumn();
        char color = token.getOppositeColor();
        Token tokenAux = new Token();
        Token tokenToRemove = new Token();
                
            if(row+2 <= 6){
            	if((this.board[row+1][column]==color)&&(this.board[row+2][column]==token.getColor())){
	                tokenToRemove.setRow(row+1);
	                tokenToRemove.setColumn(column);
	                tokenToRemove.setColor(color);
	                addToBoard(token);
               		removeFromBoard(tokenToRemove);
               		block(token.getColor());
	                return true;	
            	}
            }

            if(row-2 >= 0){
            	if((this.board[row-1][column]==color)&&(this.board[row-2][column]==token.getColor())){
                	tokenToRemove.setRow(row-1);
                	tokenToRemove.setColumn(column);
                	tokenToRemove.setColor(color);
                	addToBoard(token);
               		removeFromBoard(tokenToRemove);
               		block(token.getColor());
                	return true;
            	}                 
            }

            if(column-2 >= 0){
            	if((this.board[row][column-1]==color)&&(this.board[row][column-2]==token.getColor())){
                	tokenToRemove.setRow(row);
                	tokenToRemove.setColumn(column-1);
                	tokenToRemove.setColor(color); 
                	addToBoard(token);
               		removeFromBoard(tokenToRemove);
               		block(token.getColor());
                	return true; 
            	}
            }

            if(column+2 <= 6){
            	if((this.board[row][column+1]==color)&&(this.board[row][column+2]==token.getColor())){
                	tokenToRemove.setRow(row);
                	tokenToRemove.setColumn(column+1);
                	tokenToRemove.setColor(color);
                	addToBoard(token);
               		removeFromBoard(tokenToRemove);
               		block(token.getColor());
               		return true;
            	}
            }     
     	return false;
    }

    public boolean ganadorVertical() {
        ArrayList<Token> arr = new ArrayList<Token>();
        ArrayList<Token> visitados = new ArrayList<Token>();
        for (int j= 0; j<7 ;j++) {
          if (board [0][j] == 'b') {
            Token t = new Token(0,j,'b',false); 
            arr.add(t);
            visitados.add(t);
          }
        }
        while(!arr.isEmpty()){
          Token t1 = arr.remove(0);
          if (t1.getRow() == 6) {
            return true;
          }
          ArrayList<Token> adyacentes = obtainAdj(t1);
          for (Token t2: adyacentes) {
            if (!visitados.contains(t2) && !arr.contains(t2)) {
              visitados.add(t2);
              arr.add(t2);
            }
          }
        }
        return false;
  }

  public boolean ganadorHorizontal() {
        ArrayList<Token> arr = new ArrayList<Token>();
        ArrayList<Token> visitados = new ArrayList<Token>();
        for (int i= 0; i<7 ;i++) {
          if (board [i][0] == 'n') {
            Token t = new Token(i,0,'n',false); 
            arr.add(t);
            visitados.add(t);
          }
        }
        while(!arr.isEmpty()){
          Token t2 = arr.remove(0);
          if (t2.getColumn() == 6) {
            return true;
          }
          ArrayList<Token> adyacentes = obtainAdj(t2);
          for (Token t3: adyacentes) {
            if (!visitados.contains(t3) && !arr.contains(t3)) {
              visitados.add(t3);
              arr.add(t3);
            }
          }
        }
        return false;
  }

  public ArrayList<Token> obtainAdj(Token t){
     int row = t.getRow();
     int column = t.getColumn();
     char color = t.getColor();
     ArrayList<Token> res = new ArrayList<Token>();
    //Evalua los adyacentes de la esquina lateral izquierda
    if (row==0&&column==0) {
       if (board[row+1][column]==color) {
           Token t2 = new Token(row+1,column,color,false);
           res.add(t2);
       }
       if (board[row][column+1]==color) {
           Token t3 = new Token(row,column+1,color,false);
           res.add(t3);
       }
    }else{
        if (color=='b') {
            //Evalua los adyacentes de la esquina superior derecha
            if (row==0&&column==6) {
                if (board[row+1][column]==color) {
                   Token t4 = new Token(row+1,column,color,false);
                   res.add(t4);
               }
               if (board[row][column-1]==color) {
                   Token t5 = new Token(row,column-1,color,false);
                   res.add(t5);
               }
            }
        }else{
           if(color=='n'){
                 //Evalua los adyacentes de la esquina inferior izquierda
            if (row==6 && column==0) {
                if (board[row-1][column]==color) {
                   Token t4 = new Token(row-1,column,color,false);
                   res.add(t4);
               }
               if (board[row][column+1]==color) {
                   Token t5 = new Token(row,column+1,color,false);
                   res.add(t5);
               }
            }
           }else{
                 //Evalua los cuatro adyacentes que lo rodea
                if (row<6 && row>0 && column<6 && column>0) {
                    if (board[row+1][column]==color) {
                       Token t6 = new Token(row+1,column,color,false);
                       res.add(t6);
                   }
                   if (board[row][column+1]==color) {
                       Token t7 = new Token(row,column+1,color,false);
                       res.add(t7);
                   }
                   if (board[row][column-1]==color) {
                       Token t8 = new Token(row,column-1,color,false);
                       res.add(t8);
                   }
                }
            }
        }  
    }
    return res;
  } 
}

