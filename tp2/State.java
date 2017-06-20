import java.util.ArrayList;
import java.util.Arrays;


public class State implements AdversarySearchState {
    private State parent;
    private ArrayList<Token> tokens;
    private boolean max;
    //Cantidad de fichas jugadas por cada jugador
    private int tokensPlayer;
    private int tokensCpu;

    public State(){
        this.parent = null;
        this.tokens = new ArrayList<Token>();
        this.tokensPlayer = 0;
        this.tokensCpu = 0;
    }

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
    public State(ArrayList<Token> tokensP, boolean maxP,int tokensPlayerP,int tokensCpuP,State parentP){
        this.parent = parentP;
        this.tokens =tokensP;
        this.max = maxP;
        this.tokensPlayer=tokensPlayerP;
        this.tokensCpu=tokensCpuP;
    }


    public boolean isMax(){
        return max;
    }

    public ArrayList<Token> getTokens(){
        return this.tokens;
    }
    /*
     *@pre. true
     *@post. retorna la cantidad de tokens del Player.
    */
    public int cantTokensPlayer(){
        return  this.tokensPlayer;
    }
    /*
     *@pre. true
     *@post. retorna la cantidad de tokens del Cpu.
    */
    public int cantTokensCpu(){
        return  this.tokensCpu;
    }
    /*
     *@pre. true
     *@post. retorna el estado que precede al token actual.
    */
    public State getParent(){
        return this.parent;
    }

    /*
     *@pre. true
     *@post. retorna los tokens del Player
    */
    public ArrayList<Token> getTokensPlayer(){
      ArrayList<Token> tokensPlayer =new ArrayList<Token>();
      for (Token t: this.tokens){
        if (t.getColor()=='b')
            tokensPlayer.add(t);
      }
      return tokensPlayer;
    }

    /*
     *@pre. true
     *@post. retorna los tokens del Cpu
    */
    public ArrayList<Token> getTokensCpu(){
      ArrayList<Token> tokensCpu =new ArrayList<Token>();
      for (Token t: this.tokens){
        if (t.getColor()=='n')
            tokensCpu.add(t);
      }
      return tokensCpu;
    }
    /*
     *@pre. true
     *@post. retorna el tablero del estado actual.
    */
    public char[][]  generateBoard(){
      char [][] board=new char [7][7];
      for (char[] row: board)
                  Arrays.fill(row, '_');
      for (Token t: this.tokens)
          // llenamos la matriz con los tokens
          board[t.getRow()][t.getColumn()]=t.getColor();
      return board;
    }

    public void setTokens(ArrayList<Token> tokensP){
        this.tokens = tokensP;
    }

    public void setTokensPlayer(int tplayer){
        this.tokensPlayer = tplayer;
    }

    public void setTokensCpu(int tCPU){
        this.tokensCpu = tCPU;
    }

    public void setParent(State parentP){
        this.parent = parentP;
        if(parent.isMax()){
            this.max = false; 
        }else{
            this.max = true;
        }
    }

    public Boolean ocuppied(int i,int j){
      char [][] board =this.generateBoard();
      if(board[i][j]!='_'){
          return true;
      }
    return false; 
    }
    /** 
     * Checks whether 'this' is equal to another state. 
     * @param other is the state to compare 'this' to.
     * @return true iff 'this' is equal, as a state, to 'other'.
     * @pre. other!=null.
     * @post. true is returned iff 'this' is equal, as a state, 
     * to 'other'.
     */
    /*
     public boolean equals(State other){
        boolean containT= false;
        for (Token t : other.getTokens()) {
            if (ocuppied(t)){
                containT=true;
                break;
            }
        }
        return ((max == max)&& containT);
    }
    */
    
    /** 
     * Returns a representation as a string of the current state. 
     * @return a string representing the current state.
     * @pre. true.
     * @post. A text representation of the current state is returned.
     */
     
     public String toString(){
        //String padre= this.parent.toString();
        String max = "Es maximo: "+this.max+" | ";
        String fichas= "Cant. fichas 'b' : "+this.tokensPlayer +" | Cant. fichas 'n' : "+this.tokensCpu+"\n";
        String acum="";
        for(int i=0;i<this.tokens.size();i++) {
          acum+=","+this.tokens.get(i).toString();
        }
        String listaT="Los tokens son: \n"+acum;
        return (max+fichas+listaT);
     }
    
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