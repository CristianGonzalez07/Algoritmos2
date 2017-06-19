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
        int tokensPlayerP = parent.getTokensPlayer();
        int tokensCpuP = parent.getTokensCpu();
        State child = new State();
        Token token = new Token(); 
        char color ='_';
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                token.setRow(i);
                token.setRow(j);
                if (max) 
                    color = 'n';                    
                else
                    color ='b';
                token.setRow(color);
                if (!(child.ocuppied(token))){
                    childTokens = parentTokens.clone();
                    childTokens.add(token);
                    child.setTokens(childTokens);
                    if(max){
                        child.setTokensCpu(tokensCpuP+1);
                        child.setTokensPlayer(tokensPlayerP);
                    }else{
                        child.setTokensCpu(tokensCpuP);
                        child.setTokensPlayer(tokensPlayerP);
                    }
                    child.setParent(parent);
                    successors.add(child);
                }
            }
        }   
        return successors;
      }

    public int value(State state){
        ArrayList<Token> tokens= state.getTokens();
        char [][] board=new char [7][7]; // los campos vacios son null
        //almacenamos
        ArrayList<Token> tokensPlayer;
        ArrayList<Token> tokensCpu;
        for (char[] row: board)
            Arrays.fill(row, '_');
        for (Token t: tokens){
            // llenamos la matriz con los tokens
            board[t.getRow()][t.getColumn()]=t.getColor();
            //Obtenenmos la lista de los tokens de cada jugador en cuestion
            if (t.getColor()=='b')
                tokensPlayer.add(t);
            else
                tokensCpu.add(t);
        }
        //if (endState(state,board,tokensPlayer,tokensCpu)) {
        if (end(state)) {

            if (state.isMax())
                return maxValue();
            else
                return minValue();
        }
        
        int res = Math.abs((distance(board, tokensCpu,'n')+distance(board, tokensPlayer,'b'))-((state.getTokensCpu())+(state.getTokensPlayer())));

        return res;

    }

    //Calcula la distancia de una ficha a sus bandas
    private int distance(char[][] board, ArrayList<Token> camino, char ficha){
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
                else
                    if ((board[i-1][j]!=ficha) && (board[i+1][j]=='_')&&((i+1)<=6))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                    if ((board[i+1][j]!=ficha) && (board[i-1][j]=='_')&&((i-1)>=0))
                        return (1+ recorridoPos (board, i-1,j,ficha));
        } 
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
                else
                    if ((board[i-1][j]!=ficha) && (board[i+1][j]=='_')&&((i+1)<=6))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                    if ((board[i+1][j]!=ficha) && (board[i-1][j]=='_')&&((i-1)>=0))
                        return (1+ recorridoPos (board, i-1,j,ficha));
        } 
    }    

    /*
    //Devuelve tru si es un estado ganador
    public boolean end(State state, char[][] board,ArrayList<Token>tplayer,ArrayList<Token>tCpu){
        if (state.getTokensPlayer()==14 || state.getTokensCpu()==14)
            return true;
        if (distance(board, tplayer, 'b')==0 || distance(board, tCpu, 'n')==0)
            return true;
    return false;
    }
    */

    //Devuelve true si es un estado final
    public boolean end(State state){
        if (state.getTokensPlayer()==14 || state.getTokensCpu()==14)
            return true;
        return false;
    }

    //Devuelve el minimo valor de la euristica
    public int minValue(){
        return 14; 
    }
    //Devuelve el maximo valor de la euristica
    public int maxValue(){
        return 0; 
    }

}