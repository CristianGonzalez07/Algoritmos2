import java.util.Arrays;
import java.util.ArrayList;

public class Problem implements AdversarySearchProblem<State> { 
    
    //
    public Problem(){}

    public State initialState(){
        ArrayList<Token> tokens= new ArrayList<Token>();
        State initial= new State(tokens,false,0,0,null);
        return initial;
    }

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
      public ArrayList<State> getSuccessors(State parent) {
        ArrayList<State> successors = new ArrayList<State>(); 
        ArrayList<Token> parentTokens = parent.getTokens();
        ArrayList<Token> childTokens = new ArrayList<Token>();
        boolean max = !(parent.isMax());
        int tokensPlayerP = parent.cantTokensPlayer();
        int tokensCpuP = parent.cantTokensCpu();
        int tokensPlayer = 0;
        int tokensCpu = 0;
        State child = new State();
        Token token = new Token(); 
        char color ='_';
        int i=0;
        int j=0;
        for(i=0;i<7;i++){
            for(j=0;j<7;j++){
              childTokens = new ArrayList<Token>();
                if (max) 
                    color = 'b';                    
                else
                    color ='n';
                token = new Token(i,j,color);

                childTokens = cloneList(parentTokens);
                if(max){
                  tokensCpu = tokensCpuP;
                  tokensPlayer = (tokensPlayerP+1);
                }else{
                    tokensCpu = (tokensCpuP + 1);
                    tokensPlayer = tokensPlayerP;
                }
                child = new State(childTokens,max,tokensPlayer,tokensCpu,parent);
                if (!(child.ocuppied(i,j))){
                  childTokens.add(token);
                  child.setTokens(childTokens);
                  successors.add(child);
                }
            }
        }   
        return successors;
      }

    public int value(State state){
       
        ArrayList<Token> tokens= state.getTokens();
        char [][] board = state.generateBoard();
        //almacenamos
        ArrayList<Token> tokensPlayer = state.getTokensPlayer(); 
        ArrayList<Token> tokensCpu = state.getTokensCpu();

        int res =0;
        if (!end(state)) {
          if (state.isMax()){
              int aux =0;
              for (int i=0;i<7 ;i++ ) {
                for (int j=0;j<7 ;j++ ) {
                  if (board[i][j]=='n')
                    aux++;
                  else
                    aux--;
                }
                if(aux>=res)
                  res=aux;
                aux=0;
              }
          }else{
              int aux =0;
              for (int j=0;j<7 ;j++ ) {
                for (int i=0;i<7 ;i++ ) {
                  if (board[i][j]=='b')
                    aux++;
                  else
                    aux--;
                }
                if(aux>=res)
                  res=aux;
                aux=0;
              }
          }
        }else{
          if (state.isMax())
                return maxValue();
            else
                return minValue();
        }
      
    return res;

    }

     /*
     * @pre. state!=null.
     * @post. true is returned iff state is an end state.  
    */
    public boolean end(State state){
        if (state.cantTokensPlayer()==14 || state.cantTokensCpu()==14)
            return true;
        ArrayList<Token> tokensPlayer = state.getTokensPlayer(); 
        ArrayList<Token> tokensCpu = state.getTokensCpu();
        char[][] board = state.generateBoard();
        int cantTP = state.cantTokensPlayer();
        int cantTC = state.cantTokensCpu();
               
        return false;
    }

    public ArrayList<Token> cloneList(ArrayList<Token> list){
        ArrayList<Token> clone = new ArrayList<Token>();
        for(int i=0;i<list.size(); i++) {
          clone.add(list.get(i));
        }
        return clone;
    }

    //Devuelve el minimo valor de la euristica
    public int minValue(){
        return -14; 
    }
    //Devuelve el maximo valor de la euristica
    public int maxValue(){
        return 14; 
    }

}