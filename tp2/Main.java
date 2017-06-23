import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

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
                Token token = new Token(fila,columna,'b',false);
                if(state.ocuppied(token)){
                    System.out.println("No se puede ingresar en esa posicion.Ingrese una posicion valida o no ocupada");
                    playerPlays(state);
                }else{
                    System.out.println("Ficha Ingresada Correctamente");
                    if(!(state.willRemoved(token))){
                        state.addToBoard(token);
                    }
                    
                    state.switchState();
                }
        System.in.skip(System.in.available());
        }catch(Exception e){
            System.out.println("Ingreso Invalido"+e);
            playerPlays(state);
        }
        return state;
    }

    public static void showGame(State state){
        char [][] board = state.cloneBoard();
        System.out.println("     0 1 2 3 4 5 6");
        System.out.println("    _______________");
        for ( int i=0;i<7;i++) {
            System.out.print(" "+i+" | ");
            for (int j =0;j<7 ;j++ ) {
                System.out.print(board[i][j]+" ");
                if (j==6)
                  System.out.print("|");
            }
        System.out.println("");
        }
        System.out.println("");
    }

    public static void main(String[] args) throws Exception {
    
        Problem problem = new Problem();
        State actualState = problem.initialState();
        int depth = 3;//a eleccion
        Boolean turn = true;//turno del jugador?
        MinMaxAlphaBetaEngine<Problem,State>minMaxEngine = new MinMaxAlphaBetaEngine<Problem,State>(problem,depth);
        try{
            System.out.println("***************************************");
            while(!(problem.end(actualState))){
                System.out.println("===================================");
               showGame(actualState);
                if(turn){
                    // System.out.println("antes de jugar jugador"); 
                     //showGame(actualState);  
                     actualState = (playerPlays(actualState)).cloneState();
                     //System.out.println("despues de jugar jugador");
                     //showGame(actualState);
                }else{
                    //System.out.println("antes de jugar cpu");
                    //showGame(actualState); 
                    actualState = (minMaxEngine.computeSuccessor(actualState)).cloneState();
                    actualState.switchState();
                    //System.out.println("despues de jugar cpu");
                    //showGame(actualState); 

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