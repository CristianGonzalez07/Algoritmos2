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
                    color = 'n';                    
                else
                    color ='b';
                token = new Token(i,j,color);

                childTokens = cloneList(parentTokens);
                if(max){
                    tokensCpu = (tokensCpuP + 1);
                    tokensPlayer = tokensPlayerP;
                }else{
                    tokensCpu = tokensCpuP;
                    tokensPlayer = (tokensPlayerP+1);
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
        System.out.println("ENTRO VALUE");
        ArrayList<Token> tokens= state.getTokens();
        char [][] board = state.generateBoard();
        //almacenamos
        ArrayList<Token> tokensPlayer = state.getTokensPlayer(); 
        ArrayList<Token> tokensCpu = state.getTokensCpu();
      
        if (end(state)) {
            System.out.println("ENTRO POR ENDSTATE: ");
            if (state.isMax())
                return minValue();
            else
                return maxValue();
        }

        int res = Math.abs((distance(board, tokensCpu,'n')+distance(board, tokensPlayer,'b')));
        System.out.println("IMPRIMO RES: "+res);
        return res;

    }

    //Calcula la distancia de una ficha a sus bandas
    private int distance(char[][] board, ArrayList<Token> camino, char ficha){
        if (camino.size()!=0) {
          Token finicial= camino.get(0);
          Token ffinal = camino.get(camino.size()-1);
          //Componentes de finicial
          int ini = finicial.getColumn();
          int inj = finicial.getRow();
          //Componentes de ffinal
          int fni = ffinal.getColumn();
          int fnj = ffinal.getRow();
          if (ficha=='n')
              return ((recorridoNeg(board, ini, inj ,'n'))+(recorridoPos (board, fni, fnj ,'n')));
          else
              return ((recorridoNeg(board, fni, fnj ,'b'))+(recorridoPos (board, ini, inj ,'b')));
        }
        return minValue();
    }

    /*Desde el nodo hasta su banda de llegada
     *Calcula rodeando a las fichas del oponente (cambiar cuando pongamos
     *la funcionalidad de poder comer)*/
    private int recorridoPos(char[][] board, int i, int j, char ficha){
        if(j==6){
            return 0;
        }else{
            if (board[i][j+1]== '_') // si la siguiente posicion esta vacia
                return (1+ recorridoPos (board, i,j+1,ficha));
            else
                if (board[i][j+1]==ficha) //si la siguiente es del mismo tipo de ficha
                    return recorridoPos (board, i,j+1,ficha);
                else{
                    if (((i+1)<=6)&&((i-1)>=0)) {
                      if ((board[i-1][j]!=ficha) && (board[i+1][j]=='_'))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                      if ((board[i+1][j]!=ficha) && (board[i-1][j]=='_'))
                        return (1+ recorridoPos (board, i-1,j,ficha));
                    }
                  }
        }
        return minValue();
    }

    /*Desde el nodo hasta su banda de partida
     *Calcula rodeando a las fichas del oponente (cambiar cuando pongamos
     *la funcionalidad de poder comer)*/
    private int recorridoNeg(char[][] board, int i, int j, char ficha){
        if(j==0){
            return 0;
        }else{
            if (board[i][j-1]== '_') // si la siguiente posicion esta vacia
                return (1+ recorridoPos (board, i,j-1,ficha));
            else
                if (board[i][j-1]==ficha) //si la siguiente es del mismo tipo de ficha
                    return recorridoPos (board, i,j-1,ficha);
                else{
                  if (((i+1)<=6)&&((i-1)>=0)) {
                    if ((board[i-1][j]!=ficha) && (board[i+1][j]=='_')&&((i+1)<=6))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                    if ((board[i+1][j]!=ficha) && (board[i-1][j]=='_')&&((i-1)>=0))
                        return (1+ recorridoPos (board, i-1,j,ficha));
                  }
                }
                    
        }
        return minValue(); 
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
        int distP = distance(board,tokensPlayer,'b');
        int distC = distance(board,tokensCpu,'n');
        if (((distP==0) || (distC==0)) && ((cantTP>0) || (cantTC>0)))
            return true;
          
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
        return 120; 
    }
    //Devuelve el maximo valor de la euristica
    public int maxValue(){
        return 0; 
    }

}