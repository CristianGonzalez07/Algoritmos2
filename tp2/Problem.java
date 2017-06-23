import java.util.Arrays;
import java.util.ArrayList;

public class Problem implements AdversarySearchProblem<State> { 
    
    //
    public Problem(){}

    public State initialState(){
        ArrayList<Token> tokens= new ArrayList<Token>();
        State initial= new State(true);
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
        State child = new State(!parent.isMax());
        State childAux = new State(!parent.isMax());
        Token token = new Token(); 
        char color ='_';
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                if (parent.isMax()) 
                    color = 'b';                    
                else
                    color ='n';
                token = new Token(i,j,color,false);

                child = parent.cloneState();
                if (!(child.ocuppied(token))){
                	if(!child.willRemoved(token)){
                		child.addToBoard(token);
                	}
                  successors.add(child);
                }
            }
        } 
        return successors;
      }

    public int value(State state){
      if (end(state)){
        if (state.isMax()) 
          return maxValue();
        else
          return minValue();
      }
      //System.out.println("**********"+(valueBlack(state)+valueWhite(state)));
      return (valueBlack(state)+valueWhite(state));
    }

    private int valueWhite(State state){
      char[][] board =state.cloneBoard();     
      ArrayList<Token> arr = new ArrayList<Token>();
      ArrayList<Token> visitados = new ArrayList<Token>();
      for (int j= 0; j<7 ;j++) {
        if (board [0][j] == 'b') {
          Token t = new Token(0,j,'b',false); 
          arr.add(t);
          visitados.add(t);
        }
      }
      int acum=0;
      ArrayList<Integer> res = new ArrayList<Integer>();
      while(!arr.isEmpty()){
        Token t2 = arr.remove(0);
        acum++;
        /*if (t2.getRow() == 6) {
          //return true;
          break;
        }*/
        ArrayList<Token> adyacentes = state.obtainAdj(t2);
        for (Token t3: adyacentes) {
          if (!visitados.contains(t3) && !arr.contains(t3)) {
            acum+=valorationAdj(t3,board);
            visitados.add(t3);
            arr.add(t3);
          }
        }
        res.add(acum);
        acum=0;  
      }
      return maxInt(res);
    }

    private int valueBlack(State state){
      char[][] board =state.cloneBoard();     
      ArrayList<Token> arr = new ArrayList<Token>();
      ArrayList<Token> visitados = new ArrayList<Token>();
      for (int i= 0; i<7 ;i++) {
        if (board [i][0] == 'n') {
          Token t = new Token(i,0,'n',false); 
          arr.add(t);
          visitados.add(t);
        }
      }
      int acum=0;
      ArrayList<Integer> res = new ArrayList<Integer>();
      while(!arr.isEmpty()){
        Token t2 = arr.remove(0);
        acum++;
        /*if (t2.getColumn() == 6) {
          //return true;
          break;
        }*/
        ArrayList<Token> adyacentes = state.obtainAdj(t2);
        for (Token t3: adyacentes) {
          if (!visitados.contains(t3) && !arr.contains(t3)) {
            acum+=valorationAdj(t3,board);
            visitados.add(t3);
            arr.add(t3);
          }
        }
        res.add(acum);
        acum=0;  
      }
      return maxInt(res);
    }
    
    private int valorationAdj(Token t, char[][] board){
         int row = t.getRow();
         int column = t.getColumn();
         char color = t.getColor();
         int res = 0;
        //Evalua los adyacentes de la esquina lateral izquierda
        if (row==0&&column<6) {
           if (board[row+1][column]==color) {
               if(color=='b')
                  res+=2;
           }
           if (board[row][column+1]==color) {
               if(color=='n')
                  res+=2;
           }
        }else{
            if (row<6&&column==0) {
               if (board[row+1][column]==color) {
                   if(color=='b')
                      res+=2;
               }
               if (board[row][column+1]==color) {
                   if(color=='n')
                      res+=2;
               }
            }else{
                if (color=='b') {
                    //Evalua los adyacentes de la esquina superior derecha
                    if (row<6&&column==6) {
                        if (board[row+1][column]==color) 
                           res+=2;
                    }
                }else{
                   if(color=='n'){
                         //Evalua los adyacentes de la esquina inferior izquierda
                      if (row==6&&column<6) {
                         if (board[row][column+1]==color) 
                            res+=2;
                      }
                   }else{
                         //Evalua los cuatro adyacentes que lo rodea
                        if (row<6 && row>0 && column<6 && column>0) {
                            if (board[row+1][column]==color) {
                               if(color=='b')
                                  res+=2;
                           }
                           if (board[row][column+1]==color) {
                               if(color=='n')
                                  res+=2;
                           }
                        }
                    }
                }
            }
          }
          return res;
        }

     private int maxInt(ArrayList<Integer> arr){
        if(arr.isEmpty())
          return 0;
        int res=arr.get(0);
        for (int i=1;i<arr.size() ;i++ ) {
          if (res<arr.get(i))
            res=arr.get(i);
        }
        return res;
     }   

     /*
     * @pre. state!=null.
     * @post. true is returned iff state is an end state.  
    */
    public boolean end(State state){
        if (state.getPlayerTokens()==0 || state.getCpuTokens()==0)
            return true;
        if(state.ganadorVertical() || state.ganadorHorizontal())
          return true;
               
        return false;
    }

    public ArrayList<Token> cloneList (ArrayList<Token> list){
        ArrayList<Token> clone = new ArrayList<Token>();
        for(int i=0;i<list.size(); i++) {
          clone.add(list.get(i));
        }
        return clone;
    }

    //Devuelve el minimo valor de la euristica
    public int minValue(){
        return -50; 
    }
    //Devuelve el maximo valor de la euristica
    public int maxValue(){
        return 50; 
    }

}