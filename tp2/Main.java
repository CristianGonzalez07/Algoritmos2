import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
 
    public static Boolean insertToken(int i,int j,char color,State state){
        Token token = new Token();
        if(!(state.ocuppied(i,j))){
            token = new Token(i,j,color);
            ArrayList<Token> tokens = new ArrayList<Token>();
            tokens = state.getTokens();    
            tokens.add(token);
            state.setTokens(tokens);
            state.setTokensPlayer(state.cantTokensPlayer()+1);
            return true;        
        }else{
            return false;
        }
    }

    public static State playerPlays(State state) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean insert;
        int fila = 0;
        int columna = 0;
        try{
                System.out.println("Ingrese La ficha");
                System.out.println("Pos i :");
                fila = Integer.parseInt(br.readLine());
                System.out.println("Pos j :");
                columna = Integer.parseInt(br.readLine());
                insert = insertToken(fila,columna,'b',state);
                if(!insert){
                    System.out.println("No se puede ingresar en esa posicion.Ingrese una posicion valida o no ocupada");
                    playerPlays(state);
                }else{
                    System.out.println("Ficha Ingresada Correctamente");
                }
        System.in.skip(System.in.available());
        }catch(Exception e){
            System.out.println("Ingreso Invalido");
            playerPlays(state);
        }
        state.switchState();
        return state;
    }

    public static void showGame(State state){
        char [][] board = state.generateBoard();
        for ( int i=0;i<7;i++) {
            for (int j =0;j<7 ;j++ ) {
                System.out.print(board[i][j]+" ");
            }
        System.out.println("");
        }
    }

    public static void main(String[] args) throws Exception {
    
        Problem problem = new Problem();
        State actualState = problem.initialState();
        int depth =2;//a eleccion
        Boolean turn = true;//turno del jugador?
        MinMaxAlphaBetaEngine<Problem,State>minMaxEngine = new MinMaxAlphaBetaEngine<Problem,State>(problem,depth);
        try{
            System.out.println("***************************************");
            while(!(problem.end(actualState))){
                showGame(actualState);
                if(turn){   
                    actualState = (playerPlays(actualState)).cloneState();
                }else{
                    actualState = (minMaxEngine.computeSuccessor(actualState)).cloneState();                   
                }
                turn = !turn;//cambiar de turno
            } 
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("&&&&&&&&&&&&&&&&&&&&");
        showGame(actualState);
        /* ver valoracion de ganador
        if(problem.value(actualState) < 0){
            System.out.println("GANO EL JUGADOR!!!");
        }else{
            System.out.println("GANO LA MAQUINA!!!");
        }
        */
     
    }
}