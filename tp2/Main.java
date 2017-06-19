import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
 
    public static State insertToken(int i,int j,char color,State state){
        Token token = new Token(i,j,color);
        if(!(state.ocuppied(token))){
            ArrayList<Token> tokens = new ArrayList<Token>();
            if(state.getTokens() != null){
                tokens = state.getTokens();    
            } 
            tokens.add(token);
            state.setTokens(tokens);
            return state;        
        }else{
            return null;
        }
    }

    public static State playerPlays(State state) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        State insert = new State();
        int fila = 0;
        int columna = 0;
        try{
            while(insert == null){
                System.out.println("Ingrese La ficha");
                System.out.println("Pos i :");
                fila = Integer.parseInt(br.readLine());
                System.out.println("Pos j :");
                columna = Integer.parseInt(br.readLine());
                insert = insertToken(fila,columna,'b',state);
                if(insert == null){
                    System.out.println("No se puede ingresar en esa posicion.Ingrese una posicion valida o no ocupada");
                }else{
                    System.out.println("Ficha Ingresada Correctamente");
                }
            }
        }catch(Exception e){
            System.out.println("Ingreso Invalido");
            playerPlays(state);
        }
        
        return state;
    }

    public static void showGame(State state){
        char [][] board = new char [7][7];
        for (char[] row: board)
            Arrays.fill(row, '_');
        for (Token t: state.getTokens())
            board[t.getRow()][t.getColumn()]=t.getColor();
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
        int depth = 4;//a eleccion
        Boolean turn = true;//turno del jugador?
        MinMaxAlphaBetaEngine<Problem,State>minMaxEngine = new MinMaxAlphaBetaEngine<Problem,State>(problem,depth);
        try{
            while(!(problem.end(actualState))){
                showGame(actualState);
                if(turn){
                    actualState = playerPlays(actualState);
                }else{
                    actualState = minMaxEngine.computeSuccessor(actualState);
                }
            } 
        }catch(Exception e){
            System.out.println(e);
        }
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