import java.util.Arrays;
import java.util.ArrayList;

public class Problem implements AdversarySearchProblem{ 
    

    //
    public S initialState(){
        ArrayList<Token> tokens= new ArrayList<Token>();
        State initial= new State(tokens,false,0,0);
        return initial;
    }

    public int value(S state){//INPROCESS
        ArrayList<Token> tokens= state.getTokens();
        char [][] board=new char [7][7]; // los campos vacios son null
        //almacenamos
        ArrayList<Token> tokensPlayer;
        ArrayList<Token> tokensCPU;
        for (Token t: tokens){
            // llenamos la matriz con los tokens
            board[t.getRow()][t.getColumn()]=t.getColor();
            //Obtenenmos la lista de los tokens de cada jugador en cuestion
            if (t.getColor()=='b')
                tokensPlayer.add(t);
            else
                tokensCPU.add(t);
        }
        
        //

        //Recuperamos el turno
        boolean turno = state.isMax();
        if (turno){
            
        }



    }

    //Calcula la distancia de una ficha a sus bandas
    private int distance(char[][] board, ArrayList<Token> camino, char ficha){//INPROCESS
        Token finicial= camino.get(0);
        Token ffinal = camino.get(camino.size()-1);
        if (ficha=='n'){
            //in process
        }

    }

    /*Desde el nodo hasta su banda de llegada
     *Calcula rodeando a las fichas del oponente (cambiar cuando pongamos
     *la funcionalidad de poder comer)*/
    private int recorridoPos(char[][] board, int i, int j, char ficha){
        if(j==6){
            return 0;
        }else{
            if (board[i][j+1]== null) // si la siguiente posicion esta vacia
                return (1+ recorridoPos (board, i,j+1,ficha));
            else
                if (board[i][j+1]==ficha) //si la siguiente es del mismo tipo de ficha
                    return recorridoPos (board, i,j+1,ficha);
                else
                    if ((board[i-1][j]!=ficha) && (board[i+1][j]==null)&&((i+1)<=6))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                    if ((board[i+1][j]!=ficha) && (board[i-1][j]==null)&&((i-1)>=0))
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
            if (board[i][j-1]== null) // si la siguiente posicion esta vacia
                return (1+ recorridoPos (board, i,j-1,ficha));
            else
                if (board[i][j-1]==ficha) //si la siguiente es del mismo tipo de ficha
                    return recorridoPos (board, i,j-1,ficha);
                else
                    if ((board[i-1][j]!=ficha) && (board[i+1][j]==null)&&((i+1)<=6))
                        return (1+ recorridoPos (board, i+1,j,ficha));
                    if ((board[i+1][j]!=ficha) && (board[i-1][j]==null)&&((i-1)>=0))
                        return (1+ recorridoPos (board, i-1,j,ficha));
        } 
    }    

    //Devuelve tru si es un estado ganador
    public boolean winner(){
        return false;//hacer
    }

    //Devuelve el minimo valor de la euristica
    public int minValue(){
        return 0; //hacer
    }
    //Devuelve el maximo valor de la euristica
    public int maxValue(){
        return 10; //hacer
    }

}